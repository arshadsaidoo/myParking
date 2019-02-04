package com.example.arshad.myparking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class mapActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String LOCAL_URL = "http://ec2-18-222-194-128.us-east-2.compute.amazonaws.com:8009/selection";
    private GoogleMap mMap;
    public JSONObject closest;
    public String closest_distance;
    public JSONObject closest_spot;
    public String closest_price;
    public String closest_lat ;
    public String closest_long;
    String closest_id ;
    public double closest_lat_; // returns double primitive
    public double closest_long_;

    public JSONObject cheapest;
    public String cheapest_distance;
    public JSONObject cheapest_spot;
    public String cheapest_price;
    public String cheapest_lat ;
    public String cheapest_long;
    String cheapest_id ;
    public double cheapest_lat_; // returns double primitive
    public double cheapest_long_;
    public ImageButton click3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        click3 = findViewById(R.id.imageButton3);
        click3.setVisibility(View.INVISIBLE);
        ImageButton click=findViewById(R.id.imageButton);
        click.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //has to do the post request
                //Intent intent = new Intent(mapActivity.this,userActivity.class);
                //startActivity(intent);

                String postBody = "{\"lat\": " + 45.494514 + ", \"long\": " + -73.580624 + "}";
                try {
                    postRequest(LOCAL_URL, postBody);
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(45.494447,-73.579582)).title("Closest Parking").snippet("Price:0.25$/min Distance:10m spot 1"));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(45.497501,-73.578515)).title("Best Parking").snippet("Price:0.45$/min Distance:1m spot 2"));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(45.496629,-73.580361)).title("Cheapest Parking").snippet("Price:0.2$/min Distance:20m spot 3"));
                    //cheapest_distance=String.format("%.2f", cheapest_distance);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(cheapest_lat_,cheapest_long_)).title("Cheapest Parking").snippet("Price: "+cheapest_price+" $/min"+ " Distance: "+cheapest_distance+"m" ));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(closest_lat_,closest_long_)).title("Closest Parking").snippet("Price: "+closest_price+" $/min"+ " Distance: "+closest_distance+"m"));
                   //Toast.makeText(getApplicationContext(), cheapest_lat, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                click3.setVisibility(View.VISIBLE);

            }
        });
//
//        click3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mapActivity.this,reservationActivity.class);
//                startActivity(intent);
//            }
//        });


        ImageButton click2=findViewById(R.id.imageButton2);
        click2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(mapActivity.this,userActivity.class);
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.current_location);
        mapFragment.getMapAsync(this);
    }

    public void postRequest(String postUrl,String postBody) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, postBody);
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String response_string = response.body().string();
                JSONObject response_body = null;
                try {
                    response_body = new JSONObject(response_string);
                     closest = response_body.getJSONObject("closest");
                     closest_distance = closest.getString("distance");
                     closest_spot = closest.getJSONObject("spot");
                     closest_price = closest_spot.getString("price_minute");
                     closest_lat = closest_spot.getString("latitude");
                     closest_long = closest_spot.getString("longitude");
                     closest_id = closest_spot.getString("_id");
                     final String whole_string = "Closest: " + closest_id + "\ndistance: " + closest_distance + "\n" +
                            "lat: " + closest_lat + "\nlong: " + closest_long + "\nprice: " +
                            closest_price;
                    closest_lat_ = Double.parseDouble(closest_lat); // returns double primitive
                    closest_long_ = Double.parseDouble(closest_long);


                    cheapest = response_body.getJSONObject("cheapest");
                    cheapest_distance = cheapest.getString("distance");
                    cheapest_spot = cheapest.getJSONObject("spot");
                    cheapest_price = cheapest_spot.getString("price_minute");
                    cheapest_lat = cheapest_spot.getString("latitude");
                    cheapest_long = cheapest_spot.getString("longitude");
                    cheapest_id = cheapest_spot.getString("_id");
                    final String whole_string2 = "cheapest: " + cheapest_id + "\ndistance: " + cheapest_distance + "\n" +
                            "lat: " + cheapest_lat + "\nlong: " + cheapest_long + "\nprice: " +
                            cheapest_price;
                    cheapest_lat_ = Double.parseDouble(cheapest_lat); // returns double primitive
                   cheapest_long_ = Double.parseDouble(cheapest_long);

//                    Intent intent = new Intent(mapActivity.this,reservationActivity.class);
//                    startActivity(intent);
//
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(45.494447,-73.579582)).title("Closest Parking"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.\n.");

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
       // mMap.addMarker(new MarkerOptions().position(new LatLng(45.494447,-73.579582)).title("Closest Parking"));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(45.494447,-73.579582)).title("Closest Parking"));

        enableMyLocationIfPermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);
        //setPoiClick(googleMap);
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(47.6739881, -122.121512);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(15);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

                    mMap.setMinZoomPreference(12);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    circleOptions.radius(200);
                    circleOptions.fillColor(Color.RED);
                    circleOptions.strokeWidth(6);

                    mMap.addCircle(circleOptions);
                }
            };


    public void setPoiClick(final GoogleMap map) {

        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {
                Marker poiMarker = mMap.addMarker(new MarkerOptions()
                        .position(poi.latLng)
                        .title(poi.name));
                poiMarker.showInfoWindow();

            }

        });



    }









}