package local.webstore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "com.example.myfirstapp.URL";
    public static final String BASE_URL = "http://192.168.0.27/api/izdelek/%d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;

        RequestQueue queue = Volley.newRequestQueue(this);
        String apiUrl = "http://192.168.0.27/api/izdelki";

        final ListView listView = (ListView) findViewById(R.id.listView);

        final ArrayList itemList = new ArrayList<String>();
        final ArrayList idList = new ArrayList<Integer>();

        final Intent itemIntent = new Intent(this, DisplayMessageActivity.class);



        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONArray items = new JSONArray(response);
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);

                                String name = item.getString("name");
                                int id = item.getInt("id");

                                itemList.add(name);
                                idList.add(id);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList);

                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
                                {
                                    int id = (int) idList.get(position);

                                    itemIntent.putExtra(EXTRA_URL, String.format(BASE_URL, id));
                                    startActivity(itemIntent);
                                }
                            });

                        } catch (final JSONException e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        queue.add(stringRequest);


    }

    /** Called when the user taps the Send button */
    /*public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText editText = (EditText) findViewById(R.id.editText);
        String url = editText.getText().toString();

        intent.putExtra(EXTRA_URL, url);
        startActivity(intent);
    }*/
}
