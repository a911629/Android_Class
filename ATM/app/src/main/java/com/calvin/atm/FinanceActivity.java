package com.calvin.atm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.calvin.atm.databinding.ActivityFinanceBinding;

public class FinanceActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityFinanceBinding binding;
    private ExpenseAdapter adapter;
    private RecyclerView recyclerView;
    private ExpenseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFinanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinanceActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        helper = new ExpenseHelper( this);
        Cursor cursor = helper.getReadableDatabase()
                .query("expense",
                        null,null,null,
                        null, null ,null);

        adapter = new ExpenseAdapter(cursor);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = helper.getReadableDatabase()
                .query("expense", null,null,null,
                        null, null ,null);
        adapter = new ExpenseAdapter(cursor);
        recyclerView.setAdapter(adapter);
    }

    public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder> {
        Cursor cursor;
        public ExpenseAdapter(Cursor cursor) {
            this.cursor = cursor;
        }

        @NonNull
        @Override
        public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.expense_item, parent, false);
            return new ExpenseHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
            cursor.moveToPosition(position);
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("cdate"));
            @SuppressLint("Range") String info = cursor.getString(cursor.getColumnIndex("info"));
            @SuppressLint("Range") int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            holder.dateText.setText(date);
            holder.infoText.setText(info);
            holder.amountText.setText(String.valueOf(amount));
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();
        }

        public class ExpenseHolder extends RecyclerView.ViewHolder{
            TextView dateText;
            TextView infoText;
            TextView amountText;
            public ExpenseHolder(@NonNull View itemView) {
                super(itemView);
                dateText = itemView.findViewById(R.id.item_date);
                infoText = itemView.findViewById(R.id.item_info);
                amountText = itemView.findViewById(R.id.item_amount);
            }
        }
    }
}