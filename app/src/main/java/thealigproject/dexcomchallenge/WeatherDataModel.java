package thealigproject.dexcomchallenge;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ali on 1/13/18.
 */

public class WeatherDataModel {

    // TODO: Declare the member variables here
    private List<String> mCondition = new ArrayList<>();
    private List<String> mIconName = new ArrayList<>();
    private List<String> mHumidity = new ArrayList<>();
    private List<String> mTemperature = new ArrayList<>();

    // TODO: Create a WeatherDataModel from a JSON:
    public static WeatherDataModel fromJson(JSONObject jsonObject) {
        try {

            WeatherDataModel weatherData = new WeatherDataModel();

            List<JSONObject> jsonArray = new ArrayList<JSONObject>();
            JSONObject jsonResult;
            for(int i = 0; i <= 6; i++) {
                jsonResult = jsonObject.getJSONObject("daily").getJSONArray("data")
                        .getJSONObject(i);
                jsonArray.add(jsonResult);
            }

            List<String> conditionArray = new ArrayList<>();
            String conditionResult;
            for(int i = 0; i <= 6; i++) {
                conditionResult = jsonArray.get(i).getString("icon");
                conditionArray.add(conditionResult);
            }

            List<String> iconArray = new ArrayList<>();
            String iconResult;
            for(int i = 0; i <= 6; i++) {
                iconResult = updateWeatherIcon(conditionArray.get(i));
                iconArray.add(iconResult);
            }

            List<String> tempArray = new ArrayList<>();
            double tempResult;
            for(int i = 0; i <= 6; i++) {
                tempResult = jsonArray.get(i).getDouble("temperatureMax");
                int roundedValue = (int) Math.rint(tempResult);
                tempArray.add(Integer.toString(roundedValue));
            }

            List<String> humidityArray = new ArrayList<>();
            double humidityResult;
            for(int i = 0; i <= 6; i++) {
                humidityResult = jsonArray.get(i).getDouble("humidity");
                humidityArray.add(Double.toString(humidityResult));
            }

            String jsonCurrentCondition = jsonObject.getJSONObject("currently").getString("icon");
            double jsonCurrentHumidity = jsonObject.getJSONObject("currently").getDouble("humidity");
            double jsonCurrentTemperature = jsonObject.getJSONObject("currently").getDouble("temperature");

            for(int i = 0; i <= 6; i++) {
                weatherData.mCondition.add(conditionArray.get(i));
                weatherData.mIconName.add(iconArray.get(i));
                weatherData.mTemperature.add(tempArray.get(i));
                weatherData.mHumidity.add(humidityArray.get(i));
            }

            weatherData.mCondition.set(0, jsonCurrentCondition);
            weatherData.mIconName.set(0, updateWeatherIcon(weatherData.mCondition.get(0)));
            weatherData.mHumidity.set(0, Double.toString(jsonCurrentHumidity));
            int roundedVal = (int) Math.rint(jsonCurrentTemperature);
            weatherData.mTemperature.set(0, Integer.toString(roundedVal));

            return weatherData;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String updateWeatherIcon(String condition) {

        if (condition.equals("clear-day")) {
            return "sunny";
        } else if (condition.equals("rain")) {
            return "light_rain";
        } else if (condition.equals("snow")) {
            return "snow4";
        } else if (condition.equals("clear-night")) {
            return "night";
        } else if (condition.equals("sleet")) {
            return "sleet";
        } else if (condition.equals("wind")) {
            return "windy";
        } else if (condition.equals("fog")) {
            return "fog";
        } else if (condition.equals("cloudy")) {
            return "overcast";
        } else if (condition.equals("partly-cloudy-day")) {
            return "cloudy2";
        } else if (condition.equals("partly-cloudy-night")) {
            return "partly_cloudy_night";
        }

        return "dunno";
    }

    // TODO: Create getter methods for icon, temperature, and humidity:

    public String getIconName(int dayPosition) {
        return mIconName.get(dayPosition);
    }

    public String getTemperature(int dayPosition) {
        return mTemperature.get(dayPosition) + "˚F";
    }

    public String getHumidity(int dayPosition) {
        return "Humidity = " + mHumidity.get(dayPosition);
    }
}