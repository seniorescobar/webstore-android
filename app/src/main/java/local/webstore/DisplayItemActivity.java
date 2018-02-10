package local.webstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplayItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item);

        final TextView textName = findViewById(R.id.textName);
        final TextView textDescription = findViewById(R.id.textDescription);
        final TextView textPrice = findViewById(R.id.textPrice);

        Intent intent = getIntent();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
            Request.Method.GET,
            intent.getStringExtra(MainActivity.EXTRA_URL),
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject item = new JSONObject(response);

                        String name = item.getString("name");
                        String description = item.getString("description");
                        String price = item.getString("price");

                        textName.setText(name);
                        textDescription.setText(description);
                        textPrice.setText(price + " €");
                    } catch (final JSONException e) {
                        // Handle error
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                }
            }
        );
        queue.add(stringRequest);
    }
}