package com.unoproject.lifeuniverse.listz;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] city ={
            "Jakarta",
            "Bandung",
            "Surabaya"
    };
    List<String> ls = Arrays.asList(city);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    private void gotoActivityLain() {
        Intent i = new Intent(this, ActivityLain.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == 1) {
            gotoActivityLain();
        }
        return super.onOptionsItemSelected(item);
    }

    ListView listx;

    @Override
    protected void onPause() {
        //Toast.makeText()
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*listx = (ListView) findViewById(R.id.listv);
        List<String> ls = new ArrayList<>();
        ls.add("Bandung");
        ls.add("Jakarta");
        ls.add("Semarang");*/
        MyAdapter adapter = new MyAdapter(ls, getApplicationContext());
        listx.setAdapter(adapter);

        new FetchWeather().execute();

        // Melakukan overriding terhadap event click pada List
        listx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // String teks = "List ke-" + String.valueOf(i);
                // Toast.makeText(MainActivity.this, teks, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);

                intent.putExtra("urutan", i);
                intent.putExtra("data2", "ya...");
                startActivity(intent);
            }
        });
    }
        //
      private class FetchWeather extends AsyncTask<String, Void, String> {

            String res = "";
            HttpURLConnection conn;
            @Override
            protected String doInBackground(String... params) {
                try {
                    String s = "http://www.google.com";
                    URL url = new URL(s);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                    String data = null;

                    while ((data = reader.readLine()) != null) {
                        res += data + "\n";
                    }
                    //TextView tv = (TextView) findViewById(R.id.ya);
                    //tv.setText(res);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return res;
            }



            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(MainActivity.this, "Google Open", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                //super.onProgressUpdate(values);
            }
        }



    public static class MyAdapter extends BaseAdapter{

        List<String> data = new ArrayList();
        Context a;
        public MyAdapter(List<String> _data, Context ctx) {
            data = _data;
            a = ctx;
        }

        @Override
        public int getCount() {

            return data.size();
        }

        @Override
        public Object getItem(int i) {

            return null;
        }

        @Override
        public long getItemId(int i) {

            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater li = LayoutInflater.from(a);
            view = li.inflate(R.layout.item_list, null);
            TextView t1 = (TextView) view.findViewById(R.id.t1);
            t1.setText(data.get(i));
            TextView t2 = (TextView) view.findViewById(R.id.t2);
            //t2.setTextSize(20);
            //t2.setText("#" + data.get(i) + "$");
            t1.setTextSize(20);
            return view;
        }
    }
}
