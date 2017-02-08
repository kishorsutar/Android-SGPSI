package com.ks.ap.sgpsi;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
    Date date = new Date();
    private TextView latest_time_text;
    private TextView twentyFourPsiEast, twentyFourPsiWest, twentyFourPsiNorth, twentyFourPsiSouth, twentyFourPsiCentral, twentyFourPsiNational;
    private TextView threePsiEast, threePsiWest, threePsiNorth, threePsiSouth, threePsiCentral, threePsiNational;
    private Button refresh_button;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    private SimpleDateFormat sdfRequest = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgpsireader);
        initUi();
        fetchPSIData();
    }

    private void initUi() {
        latest_time_text = (TextView) findViewById(R.id.latest_time_text);

        twentyFourPsiCentral = (TextView) findViewById(R.id.twenty_four_central);
        twentyFourPsiNational = (TextView) findViewById(R.id.twenty_four_national);
        twentyFourPsiEast = (TextView) findViewById(R.id.twenty_four_east);
        twentyFourPsiWest = (TextView) findViewById(R.id.twenty_four_west);
        twentyFourPsiNorth = (TextView) findViewById(R.id.twenty_four_north);
        twentyFourPsiSouth = (TextView) findViewById(R.id.twenty_four_south);

        threePsiCentral = (TextView) findViewById(R.id.three_central);
        threePsiNational = (TextView) findViewById(R.id.three_national);
        threePsiEast = (TextView) findViewById(R.id.three_east);
        threePsiWest = (TextView) findViewById(R.id.three_west);
        threePsiNorth = (TextView) findViewById(R.id.three_north);
        threePsiSouth = (TextView) findViewById(R.id.three_south);

        refresh_button = (Button) findViewById(R.id.refresh_button);
        addListener();

    }

    private void addListener() {

        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchPSIData();
            }
        });
    }

    private void fetchPSIData() {
        latest_time_text.setText("Latest Updates: " + "Fetching latest data.....");
        date = new Date();
        String requestUrl = ParseString.REQUEST_URL + ParseString.DATE_TIME + sdfRequest.format(date) + ParseString.DATE + sdfDate.format(date);
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
// Request a Json response from the provided URL.
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, requestUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        parseTheJsonResponse(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SGPSIReaderActivity.this, "errO:" + error.getCause(), Toast.LENGTH_LONG).show();
                        latest_time_text.setText("Latest Updates: " + "Error while fetching data");
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
            JSONArray itemJsonArray = responseObject.getJSONArray(ParseString.ITEMS);
            JSONObject itemObject = itemJsonArray.getJSONObject(0);
            updateTime(itemObject.getString(ParseString.UPDATE_TIME_STAMP));
            updateTwentyFourHourlyValues(itemObject.getJSONObject(ParseString.READINGS).getJSONObject(ParseString.PSI_24_HOURLY));
            updateThreeHourlyValues(itemObject.getJSONObject(ParseString.READINGS).getJSONObject(ParseString.PSI_3_HOURLY));
        } catch (JSONException ex) {
            ex.getMessage();
        }
    }

    private void updateTime(String timeString) {
        latest_time_text.setText("Latest Updates: " + convertDateString(timeString));
    }

    private String convertDateString(String timeString) {
        try {
            date = sdf.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(date);
    }

    private void updateTwentyFourHourlyValues(JSONObject twentyFourHourlyObject) throws JSONException {

        String eastPsiValue = twentyFourHourlyObject.getString(ParseString.EAST);
        String westPsiValue = twentyFourHourlyObject.getString(ParseString.WEST);
        String southPsiValue = twentyFourHourlyObject.getString(ParseString.SOUTH);
        String northPsiValue = twentyFourHourlyObject.getString(ParseString.NORTH);
        String centralPsiValue = twentyFourHourlyObject.getString(ParseString.CENTRAL);
        String nationalPsiValue = twentyFourHourlyObject.getString(ParseString.NATIONAL);

        twentyFourPsiCentral.setText(centralPsiValue);
        twentyFourPsiNational.setText(nationalPsiValue);
        twentyFourPsiEast.setText(eastPsiValue);
        twentyFourPsiWest.setText(westPsiValue);
        twentyFourPsiNorth.setText(northPsiValue);
        twentyFourPsiSouth.setText(southPsiValue);

    }

    private void updateThreeHourlyValues(JSONObject threeHourlyObject) throws JSONException {
        String eastPsiValue = threeHourlyObject.getString(ParseString.EAST);
        String westPsiValue = threeHourlyObject.getString(ParseString.WEST);
        String southPsiValue = threeHourlyObject.getString(ParseString.SOUTH);
        String northPsiValue = threeHourlyObject.getString(ParseString.NORTH);
        String centralPsiValue = threeHourlyObject.getString(ParseString.CENTRAL);
        String nationalPsiValue = threeHourlyObject.getString(ParseString.NATIONAL);

        threePsiCentral.setText(centralPsiValue);
        threePsiNational.setText(nationalPsiValue);
        threePsiEast.setText(eastPsiValue);
        threePsiWest.setText(westPsiValue);
        threePsiNorth.setText(northPsiValue);
        threePsiSouth.setText(southPsiValue);
    }
}
