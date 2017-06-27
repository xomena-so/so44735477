package com.xomena.so44735477;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private float lat;
    private float lng;
    private String name;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        this.lat = i.getFloatExtra("lat", 0);
        this.lng = i.getFloatExtra("lng", 0);
        this.name = i.getStringExtra("name");

        mRequestQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng pos = new LatLng(this.lat, this.lng);
        mMap.addMarker(new MarkerOptions().position(pos).title(this.name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

        this.fetchReverseGeocodeJson();
    }

    private void fetchReverseGeocodeJson() {
        // Pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
               "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + this.lat + "%2C" + this.lng + "&result_type=locality&key=AIzaSyBrPt88vvoPDDn_imh-RzCXl5Ha2F2LYig",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("OK")) {
                                JSONArray results = response.getJSONArray("results");
                                JSONObject item = results.getJSONObject(0);
                                JSONObject geom = item.getJSONObject("geometry");
                                JSONObject bounds = geom.getJSONObject("viewport");
                                JSONObject ne = bounds.getJSONObject("northeast");
                                JSONObject sw = bounds.getJSONObject("southwest");

                                LatLngBounds mapbounds = new LatLngBounds(new LatLng(sw.getDouble("lat"),sw.getDouble("lng")),
                                        new LatLng(ne.getDouble("lat"), ne.getDouble("lng")));

                                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mapbounds, 0));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }
        );

		/* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }
}
