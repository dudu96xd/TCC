package grupo9.usjt.usjt.com.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import grupo9.usjt.usjt.com.dto.OnibusDTO;
import grupo9.usjt.usjt.com.dto.TracadoLinhaCSVDTO;
import grupo9.usjt.usjt.com.helper.utils.GTFSConsumer;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnPolylineClickListener {

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
            LatLng myLatLng = new LatLng(latitude,
                    longitude);

            CameraPosition myPosition = new CameraPosition.Builder()
                    .target(myLatLng).build();
            mMap.animateCamera(
                    CameraUpdateFactory.newCameraPosition(myPosition));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
            mMap.addMarker(new MarkerOptions().position(myLatLng).title("Localização atual."));

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.favoritos:
            //Toast.makeText(getBaseContext(),"Favoritos selecionado!",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), ListaFavoritosActivity.class);
            startActivityForResult(intent, 4);
            return(true);
        case R.id.about:
            //add the function to perform here
            return(true);
        case R.id.exit:
            //add the function to perform here
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        ButterKnife.inject(this);

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

    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void onBackPressed(){
        minimizeApp();
    }

    public android.location.Location getLocation(boolean flagZoom) {
        try {
            if(checkLocationPermission()){
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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
                            if(flagZoom){
                                mMap.animateCamera(
                                        CameraUpdateFactory.newCameraPosition(myPosition));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
                                mMap.addMarker(new MarkerOptions().position(myLatLng).title("Localização atual."));
                                mMap.clear();
                            }
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
                myLocation = this.getLocation(true);
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

    //private final String apiCredentials = getToken() ;


    @Override
    public void startActivityForResult(Intent intent, int requestCode){
        super.startActivityForResult(intent,requestCode);
    }


    public void onMapSearch(View view) {
        EditText locationSearch = findViewById(R.id.textSearch);
        String location = locationSearch.getText().toString();
        if(location.isEmpty()){
            Toast.makeText(this,"Local de pesquisa não informado.",Toast.LENGTH_LONG).show();
            return;
        }
        List<Address> addressList = null;

        Geocoder geocoder = new Geocoder(this);
        try {

            addressList = geocoder.getFromLocationName(location, 1);
            Intent intent = new Intent(getApplicationContext(), ListaLinhaActivity.class);
            intent.putExtra("input", location);
            startActivityForResult(intent, 3);


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
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        String[] param = new String[1];
        int[] param1 = new int[1];
        param1[0] = 0;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.retro));

            if (!success) {
                Log.e("2", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("2", "Can't find style. Error: ", e);
        }


        mMap.setMinZoomPreference(10.0f);

        mMap.setMaxZoomPreference(16.7f);

        onRequestPermissionsResult(0,param,param1);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-23.572847, -46.629716))      // Define o centro do mapa para localização do usuário em São Paulo
                .build();                   // define uma posição da câmera do construtor
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setOnPolylineClickListener(this);

        mMap.setTrafficEnabled(true);
        mMap.setBuildingsEnabled(false);
        if(getIntent().getSerializableExtra("listaOnibus")!=null){

            final ProgressDialog progressDialog = new ProgressDialog(this,
                    R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Carregando...");
            progressDialog.show();

            int height = 75;
            int width = 50;
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.busstoplogoicon);
            Bitmap b=bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            ArrayList<OnibusDTO> listOnibus =(ArrayList<OnibusDTO>) getIntent().getSerializableExtra("listaOnibus");
            for(OnibusDTO dto : listOnibus){
                LatLng latLng = new LatLng(dto.getLatOnibus(), dto.getLngOnibus());

                mMap.addMarker(new MarkerOptions().position(latLng).title("Horário do Onibus: "+dto.getHorario().substring(11,19)).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
            }

            GTFSConsumer parser = new GTFSConsumer(this);
            String prefixo = getIntent().getSerializableExtra("letreiroPrinc") + "-" + getIntent().getSerializableExtra("letreiroSec");
            String idTrip = parser.readTrips(prefixo);

            String[] stripsData = idTrip.split(";");
            List<TracadoLinhaCSVDTO> listaPontosTracado = parser.findPontosLinha(stripsData[0]);

            //List<String> listStopId = parser.findStopIdsByLinha(stripsData[1]);

            //List<LatLng> listStopPoints = parser.findStopPointsByStopId(listStopId);

            //height = 50;
            //width = 75;
            //bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.shelterbusstation);
            //b=bitmapdraw.getBitmap();
            //smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            //MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

            //for(LatLng latLng : listStopPoints){
            //    mMap.addMarker(markerOptions.position(latLng));
            //}

            PolylineOptions polylineOptions = new PolylineOptions().width(5)
                    .color(Color.MAGENTA);
            List<LatLng> list = new ArrayList<>();
            for(TracadoLinhaCSVDTO dto : listaPontosTracado){
                LatLng latLng = new LatLng(dto.getPy(), dto.getPx());
                list.add(latLng);
            }

            mMap.addPolyline(polylineOptions
                    .addAll(list));
            progressDialog.dismiss();

        }
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
    @Override
    public void onPolylineClick(Polyline polyline) {

    }

}