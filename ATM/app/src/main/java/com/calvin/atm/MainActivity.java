package com.calvin.atm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOGIN = 100;
    boolean logon = false;
    private List<Function> functions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "calivn onCreate: In MainActivity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //RecyclerView
        setupFunctions();

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        IconAdapter adapter = new IconAdapter();
        recyclerView.setAdapter(adapter);
        if(!logon) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN);
        }

    }

    /**
     * 設定選項圖片
     */
    private void setupFunctions() {
        functions = new ArrayList<>();
        String[] funcs = getResources().getStringArray(R.array.functions);
        functions.add(new Function(funcs[0], R.drawable.func_transaction));
        functions.add(new Function(funcs[1], R.drawable.func_balance));
        functions.add(new Function(funcs[2], R.drawable.func_finance));
        functions.add(new Function(funcs[3], R.drawable.func_contacts));
        functions.add(new Function(funcs[4], R.drawable.func_exit));
    }

    public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconHolder>{
        @NonNull
        @Override
        public IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_icon, parent, false);
            return new IconHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IconHolder holder, int position) {
            Function function = functions.get(position);
            holder.nameText.setText(function.getName());
            holder.iconImage.setImageResource(function.getIcon());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClicked(function);
                }
            });
        }

        @Override
        public int getItemCount() {
            return functions.size();
        }

        public class IconHolder extends RecyclerView.ViewHolder {
            ImageView iconImage;
            TextView nameText;
            public IconHolder(@NonNull View itemView) {
                super(itemView);
                iconImage = itemView.findViewById(R.id.item_icon);
                nameText = itemView.findViewById(R.id.item_name);
            }
        }
    }

    /**
     * 設定選項的點擊事件
     * @param function
     */
    private void itemClicked(Function function) {
        Log.d(TAG, "itemClicked: " + function.name);
        switch (function.getIcon()) {
            case R.drawable.func_transaction:
                startActivity(new Intent(this, TransActivity.class));
                break;
            case R.drawable.func_balance:
                break;
            case R.drawable.func_finance:
                Intent finance = new Intent(this, FinanceActivity.class);
                startActivity(finance);
                break;
            case R.drawable.func_contacts:
                Intent contacts = new Intent(this, ContactActivity.class);
                startActivity(contacts);
                break;
            case R.drawable.func_exit:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_LOGIN) {
            if(resultCode != RESULT_OK){
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return true;
    }
}