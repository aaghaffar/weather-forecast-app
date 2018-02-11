package thealigproject.dexcomchallenge;

// James Smith's Http client library was used for JSON response parsing
//'http://loopj.com/android-async-http/'

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class WeatherController extends AppCompatActivity {

    // Constants:
    final int REQUEST_CODE = 678;
    // Full weather url: Enter latitude and longitude
    // https://api.darksky.net/forecast/0ce8d92b388386c147050fa6e2e658eb/latitude,longitude?exclude=minutely,hourly,alerts,flags
    final String WEATHER_URL = "https://api.darksky.net/forecast/";
    // Key ID to use DarkSky data
    final String KEY = "0ce8d92b388386c147050fa6e2e658eb";
    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 5000;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1000;
    // Days of the Week
    final String[] dayArray = new String[8];

    // TODO: Set LOCATION_PROVIDER here:
    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;
    String LOCATION_PROVIDER_GPS = LocationManager.GPS_PROVIDER;


    // Member Variables
    TextView mDayLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    TextView mCityLabel;

    // TODO: Declare a LocationManager and a LocationListener here:
    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_controller_layout);

        // Link between Java code and layout elements
        mDayLabel = findViewById(R.id.dayTV);
        mWeatherImage = findViewById(R.id.weatherSymbolIV);
        mTemperatureLabel = findViewById(R.id.tempTV);
        mListView = findViewById(R.id.dayList);

        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);

        View header = View.inflate(this, R.layout.list_header, null);
        mListView.addHeaderView(header, null, false);
        mCityLabel = findViewById(R.id.cityTV);

        //Refresh will find current location and update UI accordingly
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        getWeatherForCurrentLocation();
                        Log.d("DXCM", "Refreshed");

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

                Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                List<Address> address;
                try {
                    address = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    if(address == null || address.size() == 0) {
                        mCityLabel.setText("Location Unknown");
                    } else {
                        String city = address.get(0).getLocality();
                        String state = address.get(0).getAdminArea();
                        mCityLabel.setText(city + ", " + state); //This will display the city and state.
                    }

                } catch (IOException e) {
                    Log.d("DXCM", "IOException occurred");
                } catch (NullPointerException e) {
                    Log.d("DXCM", "Null Pointer Exception occurred");
                }


                RequestParams params = new RequestParams();
                params.put("latitude", latitude);
                params.put("longitude", longitude);
                params.put("key", KEY);
                letsDoSomeNetworking(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("DXCM", "onStatusChanged() callback received");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("DXCM", "onProviderEnabled() callback received");
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

        if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
        } else {
            mLocationManager.requestLocationUpdates(LOCATION_PROVIDER_GPS, MIN_TIME, MIN_DISTANCE, mLocationListener);
            Log.d("DXCM", "GPS used");
        }

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

                mSwipeRefreshLayout.setRefreshing(false);

                //Clicking row will launch details activity which shows humidity value for that day
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(WeatherController.this, DetailActivity.class);

                        String[] humidityArray = new String[8];
                        String[] summaryArray = new String[8];

                        for(int i = 0; i < humidityArray.length; i++) {
                            humidityArray[i] = weatherData.getHumidity(i);
                        }

                        for(int i = 0; i < summaryArray.length; i++) {
                            if(i == 0) {
                                summaryArray[i] = weatherData.getSummary();
                            } else {
                                summaryArray[i] = null;
                            }

                        }

                        String message = humidityArray[position - 1];
                        intent.putExtra("humidity", message);
                        String message2 = summaryArray[position - 1];
                        intent.putExtra("summary", message2);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("DXCM", "Fail " + e.toString());
                Log.d("DXCM", "Status Code " + statusCode);
                Toast.makeText(WeatherController.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Add updateUI() here:
    private void updateUI(WeatherDataModel weather) {

        String[] tempArray = new String[8];
        Integer[] imageArray = new Integer[8];
        String[] daysOfWeekArray = new String[8];
        String[] conditionNameArray = new String[8];

        for(int i = 0; i < tempArray.length; i++) {
           tempArray[i] = weather.getTemperature(i);
        }

        for(int i = 0; i < imageArray.length; i++) {
            imageArray[i] = getResources().getIdentifier(weather.getIconName(i), "drawable", getPackageName());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d", Locale.getDefault());

        for(int i = 0; i < daysOfWeekArray.length; i++) {
            if (i == 0) {
                daysOfWeekArray[0] = "Today";
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, i);
                daysOfWeekArray[i] = sdf.format(calendar.getTime());
            }
        }

        for(int i = 0; i < conditionNameArray.length; i++) {
            conditionNameArray[i] = weather.getConditionName(i);
        }


        ListAdapter adapter = new ListAdapter(this, daysOfWeekArray, tempArray, imageArray, conditionNameArray);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("DXCM", "onPause callback received");
        //Stops updates at the onPause stage so it doesn't continuously find location
        if (mLocationManager != null) mLocationManager.removeUpdates(mLocationListener);
    }

}
