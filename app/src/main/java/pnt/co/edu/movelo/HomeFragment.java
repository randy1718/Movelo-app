package pnt.co.edu.movelo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements OnMapReadyCallback{
    private static final String TAG = "PlaceAutoCompleteAd";
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    GoogleMap map;
    String apiKey = "@string/google_maps_key";
    AutocompleteSupportFragment autocompleteFragment;
    AutocompleteSessionToken token;
    LatLng destino = new LatLng(4.666817, -74.053105);
    Geocoder geocoder = null;
    RequestQueue request;
    GoogleApi mGoogleApiClient;
    JsonObjectRequest jsonObjectRequest;
    LatLng andino;
    LatLng start;
    Double distance;
    Double distancereal;
    LatLng ubicacionCurrent;
    private final int TIME=10;
    private final ArrayList<LatLng> misPosiciones=new ArrayList<>();
    Handler handler=new Handler();

    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://chilling-castle-88137.herokuapp.com/")
            .build();

    final HerokuService3 service = retrofit.create(HerokuService3.class);
    final HerokuService4 servicio = retrofit.create(HerokuService4.class);

    Button boton;
    Button finalizar;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(27.666172, -18.273932), new LatLng(42.772283, 4.747570));
    private Object MainPage;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        geocoder = new Geocoder(getActivity());
        Places.initialize(getActivity(), "AIzaSyDlVpj8WIE_WQ8z_j8JtduOL-DXzNhQ93M");
        final PlacesClient placesClient = Places.createClient(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getParentFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        getLocalization();
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_arboles)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(new AppCompatActivity(),navController,appBarConfiguration);
        NavigationUI.setupWithNavController(opciones, navController);*/
        token = AutocompleteSessionToken.newInstance();
        autocompleteFragment = (AutocompleteSupportFragment) getParentFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.getView().setBackgroundColor(Color.parseColor("#ffffff"));
        request = Volley.newRequestQueue(getActivity());
        andino = new LatLng(4.666817, -74.053105);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        boton =view.findViewById(R.id.startRoute);
        boton.setVisibility(View.GONE);
        finalizar=view.findViewById(R.id.endRoute);
        return view;

    }

    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        HomeFragment homeFragment=new HomeFragment();
    }

    public void onDetach() {
        super.onDetach();
    }

    private void getLocalization() {
        int permiso = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permiso == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    public void actualizarUbicacion(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                final LatLng inicio = new LatLng(location.getLatitude(), location.getLongitude());
                misPosiciones.add(inicio);
                start=inicio;
                Toast.makeText(getActivity().getApplicationContext(), "Mi punto de inicio: " + inicio, Toast.LENGTH_LONG).show();
                Log.d("Distance: ", inicio.toString());
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
        assert locationManager != null;
        if (ActivityCompat.checkSelfPermission((Context)MainPage, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission((Context)MainPage, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50000, 0, locationListener);
    }

    public void iniciarRuta() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                actualizarUbicacion();
            }
        },TIME);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        /*LatLng bogota=new LatLng(4.627195, -74.081038);
        map.addMarker(new MarkerOptions().position(bogota).title("Bogota"));
        map.moveCamera(CameraUpdateFactory.newLatLng(bogota));*/

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().isMapToolbarEnabled();


        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setCompassEnabled(false);



        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                ArrayList<LatLng> points = new ArrayList<>();
                PolylineOptions lineOptions = new PolylineOptions();
                final LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                map.addMarker(new MarkerOptions().position(miUbicacion).title("ubicacion actual"));
                SharedPreferences pref1=getActivity().getApplicationContext().getSharedPreferences("ubicacionlat",Context.MODE_PRIVATE);
                SharedPreferences pref2=getActivity().getApplicationContext().getSharedPreferences("ubicacionlon",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1= pref1.edit();
                SharedPreferences.Editor editor2= pref2.edit();
                editor1.putString("ubicacionLat", String.valueOf(miUbicacion.latitude));
                editor2.putString("ubicacionLon", String.valueOf(miUbicacion.longitude));
                editor1.apply();
                editor2.apply();
                map.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(miUbicacion)
                        .zoom(15)
                        .bearing(0)
                        .build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



                autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(@NotNull Place place) {
                        // TODO: Get info about the selected place.
                        Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                        SharedPreferences prefs=getActivity().getSharedPreferences("Place",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor= prefs.edit();
                        editor.putString("place",place.getName());
                        editor.apply();

                        try {

                            List<Address>addresses=geocoder.getFromLocationName(place.getName(),2);
                            List<MarkerOptions> markers=new ArrayList<>();
                            Double lat=addresses.get(0).getLatitude();
                            Double lon=addresses.get(0).getLongitude();
                            boton.setVisibility(View.VISIBLE);
                            LatLng d=new LatLng(lat,lon);
                            destino=d;
                            Double distancia=getDistancia(miUbicacion,destino);
                            distance=distancia;
                            Toast.makeText(getActivity().getApplicationContext(), "Distancia: "+distancia, Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity().getApplicationContext(), "Mi punto inicial: "+start, Toast.LENGTH_LONG).show();

                            map.clear();
                            MarkerOptions mark=new MarkerOptions().position(destino).title("Destino");
                            map.addMarker(new MarkerOptions().position(miUbicacion).title("ubicacion actual"));
                            map.addMarker(mark);
                            new TaskDirectionRequest().execute(buildRequestUrl(miUbicacion,destino));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        boton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boton.setVisibility(View.GONE);
                                finalizar.setVisibility(View.VISIBLE);
                                map.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(miUbicacion)
                                        .zoom(20)
                                        .bearing(20)
                                        .tilt(85)
                                        .build();
                                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                iniciarRuta();
                                map.addMarker(new MarkerOptions().position(miUbicacion).title("ubicacion actual").icon(BitmapDescriptorFactory.fromResource(R.drawable.ciclist)));
                                SharedPreferences prefs=((Context)MainPage).getSharedPreferences("Shared_email",Context.MODE_PRIVATE);
                                final String correo=prefs.getString("email","");
                                Call<ResponseBody> llamado=servicio.agregarPuntos(distancereal,correo);
                                llamado.enqueue(new Callback<ResponseBody>(){
                                    @Override
                                    public void onResponse (Call < ResponseBody > call, Response< ResponseBody > response){

                                        try {
                                            String respuesta=response.body().string();
                                            Toast.makeText(getActivity().getApplicationContext(), "Mensaje: "+respuesta +" - "+ correo, Toast.LENGTH_LONG).show();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                            Toast.makeText(getActivity().getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure (Call < ResponseBody > call, Throwable t){
                                        t.printStackTrace();
                                        Toast.makeText(getActivity().getApplicationContext(), "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });

                        finalizar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences prefs=((Context)MainPage).getSharedPreferences("Place",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor= prefs.edit();
                                editor.putString("place",null);
                                editor.apply();
                                map.clear();
                                map.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(miUbicacion)
                                        .zoom(15)
                                        .bearing(0)
                                        .build();
                                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                finalizar.setVisibility(View.GONE);
                            }
                        });



                        //assert destino != null;

                    }
                    @Override
                    public void onError(@NotNull Status status) {
                        // TODO: Handle the error.
                        Log.i(TAG, "An error occurred: " + status);
                    }
                });

                //map.addPolyline(new PolylineOptions().add(miUbicacion,andino).color(Color.BLUE).width(5).geodesic(true));
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

        Intent in=getActivity().getIntent();
        Bundle b=in.getExtras();
        String p1;
        Double la;
        Double lo;
        if(b!=null){
            p1= (String) b.get("place");
            la=Double.parseDouble(b.getString("lat"));
            lo=Double.parseDouble(b.getString("lon"));
            LatLng Ub=new LatLng(la,lo);
            Log.d("Nombre place ",p1);
            try {
                List<Address>addresses=geocoder.getFromLocationName(p1,2);
                List<MarkerOptions> markers=new ArrayList<>();
                Double lat=addresses.get(0).getLatitude();
                Double lon=addresses.get(0).getLongitude();
                boton.setVisibility(View.VISIBLE);
                LatLng d=new LatLng(lat,lon);
                destino=d;
                Double distancia=getDistancia(Ub,destino);
                distance=distancia;
                Toast.makeText(getActivity(), "Distancia: "+distancia, Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Mi punto inicial: "+start, Toast.LENGTH_LONG).show();

                map.clear();
                MarkerOptions mark=new MarkerOptions().position(destino).title("Destino");
                map.addMarker(new MarkerOptions().position(Ub).title("ubicacion actual"));
                map.addMarker(mark);
                new TaskDirectionRequest().execute(buildRequestUrl(Ub,destino));
                p1=null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        assert locationManager != null;
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50000, 0, locationListener);
    }

    private String buildRequestUrl(LatLng origin, LatLng destination) {
        String strOrigin = "origin=" + origin.latitude + "," + origin.longitude;
        String strDestination = "destination=" + destination.latitude + "," + destination.longitude;
        String sensor = "sensor=false";
        String mode = "mode=cycling"; //Genera una ruta para ciclistas

        String param = strOrigin + "&" + strDestination + "&" + sensor + "&" + mode;
        String output = "json";
        String APIKEY = getResources().getString(R.string.google_maps_key);

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param + "&key="+APIKEY;
        Log.d("TAG", url);
        return url;
    }

    private String requestDirection(String requestedUrl) {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(requestedUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            responseString = stringBuffer.toString();
            bufferedReader.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        assert httpURLConnection != null;
        httpURLConnection.disconnect();
        return responseString;
    }

    public class TaskDirectionRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String responseString) {
            super.onPostExecute(responseString);
            //Json object parsing
            TaskParseDirection parseResult = new TaskParseDirection();
            parseResult.execute(responseString);
        }
    }

    public class TaskParseDirection extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonString) {
            List<List<HashMap<String, String>>> routes = null;
            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(jsonString[0]);
                DirectionParser parser = new DirectionParser();
                routes = parser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            super.onPostExecute(lists);
            ArrayList points = null;
            ArrayList<LatLng> puntos=new ArrayList<>();
            ArrayList<Punto> coordenadas=null;
            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                coordenadas=new ArrayList<>();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lng"));

                    points.add(new LatLng(lat, lon));
                    puntos.add(new LatLng(lat, lon));
                    Punto p=new Punto(lat,lon);
                    coordenadas.add(p);
                }

                SharedPreferences prefs=getActivity().getSharedPreferences("Shared_email",Context.MODE_PRIVATE);
                final String correo=prefs.getString("email","");

                Call<ResponseBody> call = service.guardarRuta(coordenadas,correo);
                call.enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse (Call < ResponseBody > call, Response< ResponseBody > response){

                        try {
                            String respuesta=response.body().string();
                            Toast.makeText(getActivity().getApplicationContext(), "Mensaje: "+respuesta +" - "+ correo, Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getActivity().getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure (Call < ResponseBody > call, Throwable t){
                        t.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                Double distanciaCorta=0.0;
                int contador=0;
                for(int i=0;i<puntos.size();i++){
                    contador+=1;
                    int fin=i+1;
                    if(i==0) {
                        Double distance = getDistancia(puntos.get(0), puntos.get(i));
                        distanciaCorta +=distance;
                        Log.d("Distance "+i+"-"+fin, distance.toString());
                    }else if (i>=1) {
                        Double distance=getDistancia(puntos.get(i-1), puntos.get(i));
                        distanciaCorta +=distance;
                        Log.d("Distance "+i+"-"+fin, distance.toString());
                    }
                }
                distancereal=distanciaCorta;
                Toast.makeText(getActivity().getApplicationContext(), "Distancia coordenadas: "+distanciaCorta, Toast.LENGTH_LONG).show();

                polylineOptions.addAll(points);
                polylineOptions.width(30f);
                polylineOptions.color(Color.parseColor("#02717C")); // Color de Movelo
                polylineOptions.geodesic(true);
            }
            if (polylineOptions != null) {
                map.addPolyline(polylineOptions);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Direction not found", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Double getDistancia(LatLng inicio, LatLng end){
        Double distancia=0.0;

        Double difLat=(Math.PI/180)*(end.latitude-inicio.latitude);
        Double difLon=(Math.PI/180)*(end.longitude-inicio.longitude);

        Double a=Math.pow(Math.sin(difLat/2),2)+Math.cos((Math.PI/180)*(inicio.latitude))*Math.cos((Math.PI/180)*(end.latitude))*Math.pow(Math.sin(difLon/2),2);
        distancia=2*Math.atan(Math.sqrt(a)/Math.sqrt(1-a));
        return distancia*6378.0;
    }
}