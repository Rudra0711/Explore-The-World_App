package com.rudraksh.mapsexample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.PluralsRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private EditText inputPlace;
    private ImageView searchIcon;
    private volatile LatLng userLocation;
    private volatile LatLng userLastLocation;

    private AdView adView;
    public static InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //IT TAKES A CALLBACK OBJECT,WHICH WE ALREADY IMPLEMENTED
        // THIS METHOD SETS A CALLBACK OBJ WHICH IS TRIGGERED WHEN THE GOOGLE MAP IS READY TO BE USED
        /** Takes us onto the onMapReady method */
        mapFragment.getMapAsync(this);

        inputPlace = findViewById(R.id.inputPlace);
        searchIcon = findViewById(R.id.searchIcon);

        //ALL THE "MANAGERS" ARE MAINLY USED FOR GETTING THE SYSTEM SERVICE
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        //GETTING CHANGED USER LOCATION
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //TO CLEAR THE PREVIOUS MARKER
                mMap.clear();

                //Toast.makeText(getApplicationContext(),"Location"+location.toString(),Toast.LENGTH_SHORT).show();
                Log.i("Location : ", location.toString());

                userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {

                    List<Address> addresses = geocoder.getFromLocation(userLocation.latitude, userLocation.longitude, 1);

                    if (addresses != null && addresses.size() > 0) {
                        mMap.addMarker(new MarkerOptions().position(userLocation).title(addresses.get(0).getAddressLine(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                    } else {
                        mMap.addMarker(new MarkerOptions().position(userLocation).title("Oops!Could not load the address").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 19));


                } catch (IOException e) {

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

        MobileAds.initialize(this,"[ADD ID]");

        AdRequest adRequest=new AdRequest.Builder().addTestDevice("[TEST DEVICE ID]").build();

        adView=findViewById(R.id.adView);
        adView.loadAd(adRequest);

        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId("[ADD UNIT ID]");
        interstitialAd.loadAd(adRequest);

        int check=getIntent().getExtras().getInt("ADS",0);

        if (check==1){

            interstitialAd.setAdListener(new AdListener(){

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();

                    if (interstitialAd.isLoaded()){
                        interstitialAd.show();
                    }
                }
            });
        }
    }

    //THIS METHOD WILL CHECK WHETHER THE USER GRANTED PERMISSION OR NOT
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, locationListener);

                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setBuildingsEnabled(true);


        //VERIFYING PERMISSION
        if (Build.VERSION.SDK_INT > 23) {

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 50, locationListener);

                //TO START THE MAP WITH THE LOCATION WE LEFT IT WITH
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                mMap.clear();

                try {
                     userLastLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                    try {

                        List<Address> addresses = geocoder.getFromLocation(userLastLocation.latitude, userLastLocation.longitude, 1);

                        Log.i("GeoCoder Location", addresses.get(0).toString());


                        if (addresses != null && addresses.size() > 0) {
                            mMap.addMarker(new MarkerOptions().position(userLastLocation).title(addresses.get(0).getAddressLine(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                        } else {
                            mMap.addMarker(new MarkerOptions().position(userLastLocation).title("Oops!Could not load the address").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation, 19));
                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                } catch (NullPointerException ex) {
                    ex.printStackTrace();

                    Toast.makeText(getApplicationContext(), "Oops!Could not locate your location", Toast.LENGTH_SHORT).show();
                }

            }
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                LatLng search = new LatLng(latLng.latitude, latLng.longitude);

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {

                    List<Address> addresses = geocoder.getFromLocation(search.latitude, search.longitude, 1);

                    Log.i("GeoCoder Location", addresses.get(0).toString());

                    if (addresses != null && addresses.size() > 0) {
                        mMap.addMarker(new MarkerOptions().position(search).title(addresses.get(0).getAddressLine(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    } else {
                        mMap.addMarker(new MarkerOptions().position(search).title("Oops!Could not load the address").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(search, 19));

                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
        });

    }


        public void searchPlace (View view)
        {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

            try {

                List<Address> addresses = geocoder.getFromLocationName(inputPlace.getText().toString(), 1);

                if (addresses != null && addresses.size() > 0) {
                    LatLng yourPlace = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    mMap.addMarker(new MarkerOptions().position(yourPlace).title(addresses.get(0).getAddressLine(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourPlace, 19));

                    inputPlace.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Could not locate!.\nPlease enter the correct address (area) of the place along with its name", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void jumpToCurrent (View view)
        {
           try {
               mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocation, 19));

           }catch (NullPointerException ex){
               Toast.makeText(getApplicationContext(),"Error finding your location!\nMake sure that you are not indoor",Toast.LENGTH_LONG).show();
           }
        }

}