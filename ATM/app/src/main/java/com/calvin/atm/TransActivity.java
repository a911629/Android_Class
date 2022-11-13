package com.calvin.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransActivity extends AppCompatActivity {

    private static final String TAG = TransActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        recyclerView = findViewById(R.id.recycler_trans);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://atm201605.appspot.com/h")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Log.d(TAG, "onResponse: " + json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseGson(json);
                    }
                });
            }
        });
    }

    private void parseGson(String json) {
        Gson gson = new Gson();
        transactions = gson.fromJson(json,
                new TypeToken<ArrayList<Transaction>>(){}.getType());
        TransAdapter adapter = new TransAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void parseJson(String json) {
        transactions = getTransactions();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Transaction tran = new Transaction(object);
                transactions.add(new Transaction(object));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TransAdapter adapter = new TransAdapter();
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    private List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        return transactions;
    }

    public class TransAdapter extends RecyclerView.Adapter<TransAdapter.TransHolder> {

        @NonNull
        @Override
        public TransHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_transaction, parent, false);
            return new TransHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TransHolder holder, int position) {
            Transaction tran = transactions.get(position);
            holder.bindTo(tran);
        }

        @Override
        public int getItemCount() {
            return transactions.size();
        }

        public class TransHolder extends RecyclerView.ViewHolder {
            TextView dateText;
            TextView amountText;
            TextView typeText;
            public TransHolder(@NonNull View itemView) {
                super(itemView);
                dateText = itemView.findViewById(R.id.trans_date);
                amountText = itemView.findViewById(R.id.trans_amount);
                typeText = itemView.findViewById(R.id.trans_type);
            }

            public void bindTo(Transaction tran) {
                dateText.setText(tran.getDate());
                amountText.setText(String.valueOf(tran.getAmount()));
                typeText.setText(String.valueOf(tran.getType()));
            }
        }
    }

    public class TransTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: " + s);
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb = new StringBuilder();
            try {
//                URL url = new URL("http://atm201605.appspot.com/h");
                URL url = new URL(strings[0]);
                InputStream is = url.openStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                sb = new StringBuilder();
                String line = in.readLine();
                while (line != null) {
                    sb.append(line);
                    line = in.readLine();
                }
                Log.d(TAG, "doInBackground: " + sb.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }
}
