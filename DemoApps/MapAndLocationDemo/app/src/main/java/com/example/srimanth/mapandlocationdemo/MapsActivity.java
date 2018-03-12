package com.example.srimanth.mapandlocationdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode==1)
        {
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //clear the previous markers
                mMap.clear();

                // Add a marker in Sydney and move the camera

                LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(myLocation).title("Marker in "+location.toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                //Toast.makeText(MapsActivity.this, "The location is " + location.toString(), Toast.LENGTH_SHORT).show();


                Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                try {


                    List<Address> listAddresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                    if (listAddresses!=null && listAddresses.size()>0)
                    {
                        String address="";

                        if (listAddresses.get(0).getThoroughfare()!=null)
                        {
                            address+=listAddresses.get(0).getThoroughfare()+";";
                        }
                        if (listAddresses.get(0).getLocality()!=null)
                        {
                            address+=listAddresses.get(0).getLocality()+ ";";
                        }
                        if (listAddresses.get(0).getPostalCode()!=null)
                        {
                            address+=listAddresses.get(0).getPostalCode()+ ";";
                        }
                        if (listAddresses.get(0).getAdminArea()!=null)
                        {
                            address+=listAddresses.get(0).getAdminArea();
                        }

                        Toast.makeText(MapsActivity.this,address,Toast.LENGTH_LONG).show();
                        Log.i("Address",address);

                        //Log.i("Place Info",listAddresses.get(0).toString());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        else
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            else
            {

                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,0,0,locationListener);

                Location lastKnownLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                //clear the previous markers
                mMap.clear();

                // Add a marker in Sydney and move the camera

                LatLng myLocation = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(myLocation).title("Marker in "+lastKnownLocation.toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

            }
        }
    }
}
