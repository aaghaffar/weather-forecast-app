package thealigproject.dexcomchallenge;

// James Smith's Http client library was used for JSON response parsing
//'http://loopj.com/android-async-http/'

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WeatherController extends AppCompatActivity {

    // Constants:
    final int REQUEST_CODE = 123;
    final String WEATHER_URL = "https://api.darksky.net/forecast/";
    // App ID to use DarkSky data
    final String KEY = "0ce8d92b388386c147050fa6e2e658eb";
    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 5000;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1000;

    // TODO: Set LOCATION_PROVIDER here:
    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

    // Member Variables:
    TextView mDayLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    // TODO: Declare a LocationManager and a LocationListener here:
    LocationManager mLocationManager;
    LocationListener mLocationListener;

    // ListView Setup for days of the week
    private ListView mListView;
    String[] dayArray = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_controller_layout);

        // Link between Java code and layout elements
        mDayLabel = findViewById(R.id.dayTV);
        mWeatherImage = findViewById(R.id.weatherSymbolIV);
        mTemperatureLabel = findViewById(R.id.tempTV);
        mListView  = findViewById(R.id.dayList);
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);

        //Refresh will find current location and update UI accordingly
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        getWeatherForCurrentLocation();
                        Log.d("DXCM", "Refreshed" );
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }
        );
    }

    //onResume contains main method call to retrieve location and consequently update UI
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DXCM", "onResume() called");

        Log.d("DXCM", "Getting weather for current location");
        getWeatherForCurrentLocation();

    }

    //Method to get longitude and latitude for location and then weather data
    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("DXCM", "onLocationChanged() callback received");

                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                Log.d("DXCM", "longitude is " + longitude);
                Log.d("DXCM", "latitude is " + latitude);

                RequestParams params = new RequestParams();
                params.put("latitude", latitude);
                params.put("longitude", longitude);
                params.put("key", KEY);
                letsDoSomeNetworking(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("DXCM", "onProviderDisabled() callback received");
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            return;
        }
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("DXCM", "onRequestPermissionResult(): Permission Granted!");
                getWeatherForCurrentLocation();
            } else {
                Log.d("DXCM", "Permission Denied!");

            }
        }
    }


    //letsDoSomeNetworking uses James Smith's library for an http request
    private void letsDoSomeNetworking(String latitude, String longitude) {

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(WEATHER_URL + KEY + "/" + latitude + "," + longitude + "?" + "exclude=minutely,hourly,alerts,flags" , new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DXCM", "Success! JSON: " + response.toString());

                //WeatherDataModel used to parse JSON
                final WeatherDataModel weatherData = WeatherDataModel.fromJson(response);

                updateUI(weatherData);

                //Clicking row will launch details activity which shows humidity value for that day
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(WeatherController.this, DetailActivity.class);

                        String[] humidityArray = {weatherData.getHumidity(dayArray[0]), weatherData.getHumidity(dayArray[1]), weatherData.getHumidity(dayArray[2]),
                                weatherData.getHumidity(dayArray[3]), weatherData.getHumidity(dayArray[4]), weatherData.getHumidity(dayArray[5]), weatherData.getHumidity(dayArray[6])};

                        String message = humidityArray[position];
                        intent.putExtra("humidity", message);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("DXCM", "Fail " + e.toString());
                Log.d("DXCM", "Status Code" + statusCode);
                Toast.makeText(WeatherController.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Add updateUI() here:
    private void updateUI(WeatherDataModel weather) {

        String[] tempArray = {weather.getTemperature(dayArray[0]), weather.getTemperature(dayArray[1]), weather.getTemperature(dayArray[2]), weather.getTemperature(dayArray[3]),
                weather.getTemperature(dayArray[4]), weather.getTemperature(dayArray[5]), weather.getTemperature(dayArray[6])};

        int resourceID = getResources().getIdentifier(weather.getIconName(dayArray[0]), "drawable", getPackageName());
        int resourceID1 = getResources().getIdentifier(weather.getIconName(dayArray[1]), "drawable", getPackageName());
        int resourceID2 = getResources().getIdentifier(weather.getIconName(dayArray[2]), "drawable", getPackageName());
        int resourceID3 = getResources().getIdentifier(weather.getIconName(dayArray[3]), "drawable", getPackageName());
        int resourceID4 = getResources().getIdentifier(weather.getIconName(dayArray[4]), "drawable", getPackageName());
        int resourceID5 = getResources().getIdentifier(weather.getIconName(dayArray[5]), "drawable", getPackageName());
        int resourceID6 = getResources().getIdentifier(weather.getIconName(dayArray[6]), "drawable", getPackageName());


        Integer[] imageArray = {resourceID, resourceID1, resourceID2, resourceID3, resourceID4, resourceID5, resourceID6};

        ListAdapter adapter = new ListAdapter (this, dayArray, tempArray, imageArray);

        mListView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Stops updates at the onPause so it doesn't continuously find location
        if (mLocationManager != null) mLocationManager.removeUpdates(mLocationListener);
    }

}
