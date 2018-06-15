package grupo9.usjt.usjt.com.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import butterknife.InjectView;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    @InjectView(R.id.search_button)
    Button searchButton;
    @InjectView(R.id.textSearch)
    EditText textSearch;
    private GoogleMap mMap;
    LocationManager locationManager;
    Location myLocation;
    boolean isGPSEnabled,isNetworkEnabled,canGetLocation;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60;
    double latitude,longitude;

    LocationListener locationListenerGPS=new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            //String msg="New Latitude: "+latitude + "New Longitude: "+longitude;
            //Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private Location getLastKnownLocation(){
        if( ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ){
            return null;
        }
        Location bestLocation = null;
        LocationManager locationManager =
                (LocationManager) this.getSystemService( LOCATION_SERVICE );
        if(locationManager!=null) {
            List<String> providers = locationManager.getProviders(true);
            for (String provider : providers) {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l; // Found best last known location;
                }
            }
        }
        else{
            Log.e("getLastKnownLocation()","Serviço de localização indisponível!");
            Toast.makeText(getBaseContext(),"Serviço de localização indisponível!",Toast.LENGTH_LONG).show();
            onBackPressed();
        }
        return bestLocation;
    }

    public android.location.Location getLocation() {
        try {
            if(checkLocationPermission()){
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                // getting GPS status
                assert locationManager != null;
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // getting network status
                if (myLocation == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListenerGPS);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        myLocation = getLastKnownLocation();
                        if (myLocation != null) {
                            isNetworkEnabled = true;
                            latitude = myLocation.getLatitude();
                            longitude = myLocation.getLongitude();
                        }
                    }
                }

                if (isGPSEnabled || isNetworkEnabled) {
                    this.canGetLocation = true;
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListenerGPS);
                        Log.d("Network", "Network Enabled");
                        if (locationManager != null) {
                            myLocation = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            LatLng myLatLng = new LatLng(latitude,
                                    longitude);

                            CameraPosition myPosition = new CameraPosition.Builder()
                                    .target(myLatLng).build();
                            mMap.animateCamera(
                                    CameraUpdateFactory.newCameraPosition(myPosition));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
                            mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(myLatLng).title("Localização atual."));
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (myLocation == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListenerGPS);
                            Log.d("GPS", "GPS Enabled");
                            if (locationManager != null) {
                                myLocation = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            }
                        }
                    }
                } else {
                    Toast.makeText(getBaseContext(),"Internet indisponível!",Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myLocation;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            onStop();

            if(!(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                //Sem permissão de localização a aplicação não pode funcionar
                System.exit(0);
            }
            else{
                myLocation = this.getLocation();
            }
        } else {
            switch (requestCode) {

                case 0: {
                    if (!(grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                        //Sem permissão de localização a aplicação não pode funcionar
                        System.exit(0);
                    }
                }
                // other 'case' lines to check for other
                // permissions this app might request
            }
        }
    }

    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public void onMapSearch(View view) {
        EditText locationSearch = findViewById(R.id.textSearch);
        String location = locationSearch.getText().toString();
        if(location.equals("")){
            Toast.makeText(this,"Local de pesquisa não informado.",Toast.LENGTH_LONG).show();
            return;
        }
        List<Address> addressList = null;

        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocationName(location, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addressList!=null) {
            if(!addressList.isEmpty()) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
        else{
            Toast.makeText(this, "Local não encontrado",Toast.LENGTH_LONG).show();
        }
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
        String[] param = new String[1];
        int[] param1 = new int[1];
        param1[0] = 0;

        onRequestPermissionsResult(0,param,param1);
        myLocation = this.getLocation();

        mMap.setTrafficEnabled(true);
        mMap.setBuildingsEnabled(false);

    }

    private LatLngBounds adjustBoundsForMaxZoomLevel(LatLngBounds bounds) {
        LatLng sw = bounds.southwest;
        LatLng ne = bounds.northeast;
        double deltaLat = Math.abs(sw.latitude - ne.latitude);
        double deltaLon = Math.abs(sw.longitude - ne.longitude);

        final double zoomN = 0.005; // minimum zoom coefficient
        if (deltaLat < zoomN) {
            sw = new LatLng(sw.latitude - (zoomN - deltaLat / 2), sw.longitude);
            ne = new LatLng(ne.latitude + (zoomN - deltaLat / 2), ne.longitude);
            bounds = new LatLngBounds(sw, ne);
        }
        else if (deltaLon < zoomN) {
            sw = new LatLng(sw.latitude, sw.longitude - (zoomN - deltaLon / 2));
            ne = new LatLng(ne.latitude, ne.longitude + (zoomN - deltaLon / 2));
            bounds = new LatLngBounds(sw, ne);
        }

        return bounds;
    }
}