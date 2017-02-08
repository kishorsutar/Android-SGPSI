package com.ks.ap.sgpsi;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kishorsutar on 2/6/17.
 */
public class SGPSIReaderActivity extends AppCompatActivity {
    TextView latest_time_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgpsireader);
        latest_time_text = (TextView) findViewById(R.id.latest_time_text);

        fetchPSIData();
    }

    private void fetchPSIData() {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.data.gov.sg/v1/environment/psi?date_time=2017-02-04T09:45:00&date=2017-02-04";

// Request a Json response from the provided URL.
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        parseTheJsonResponse(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SGPSIReaderActivity.this, "errO:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML");
                params.put("api-key", "quZMY0KZdI0oaw82bAFrfNGnk7GU1Ywp");
                return params;
            }
        };


// Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

    private void parseTheJsonResponse(JSONObject responseObject) {
        try {
            JSONArray itemJsonArray = responseObject.getJSONArray("items");
            JSONObject itemObject = itemJsonArray.getJSONObject(0);
            updateTime(itemObject.getString("update_timestamp"));


        } catch (JSONException ex) {
            ex.getMessage();
        }
    }

    private void updateTime(String timeString) {


        latest_time_text.setText("Latest Update: " + converDateString(timeString));

    }

    private String converDateString(String timeString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date convertedDate = new Date();
        try {
            convertedDate = sdf.parse(timeString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String s = dateFormat.format(convertedDate);
        return s;
    }
}
