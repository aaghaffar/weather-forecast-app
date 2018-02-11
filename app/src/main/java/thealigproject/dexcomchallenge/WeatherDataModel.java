package thealigproject.dexcomchallenge;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 1/13/18.
 */

public class WeatherDataModel {

    // TODO: Declare the member variables here
    private List<String> mCondition = new ArrayList<>();
    private List<String> mIconName = new ArrayList<>();
    private List<String> mTemperature = new ArrayList<>();
    private List<String> mHumidity = new ArrayList<>();
    private List<String> mConditionName = new ArrayList<>();
    private String mSummary;

    // TODO: Create a WeatherDataModel from a JSON:
    public static WeatherDataModel fromJson(JSONObject jsonObject) {
        try {

            WeatherDataModel weatherData = new WeatherDataModel();

            List<JSONObject> jsonList = new ArrayList<JSONObject>();
            JSONObject jsonResult;
            for(int i = 0; i <= 7; i++) {
                jsonResult = jsonObject.getJSONObject("daily").getJSONArray("data")
                        .getJSONObject(i);
                jsonList.add(jsonResult);
            }

            List<String> conditionList = new ArrayList<>();
            String conditionResult;
            for(int i = 0; i <= 7; i++) {
                conditionResult = jsonList.get(i).getString("icon");
                conditionList.add(conditionResult);
            }

            List<String> iconList = new ArrayList<>();
            String iconResult;
            for(int i = 0; i <= 7; i++) {
                iconResult = updateWeatherIcon(conditionList.get(i));
                iconList.add(iconResult);
            }

            List<String> tempList = new ArrayList<>();
            double tempResult;
            for(int i = 0; i <= 7; i++) {
                tempResult = jsonList.get(i).getDouble("temperatureMax");
                int roundedValue = (int) Math.rint(tempResult);
                tempList.add(Integer.toString(roundedValue));
            }

            List<String> humidityList = new ArrayList<>();
            double humidityResult;
            for(int i = 0; i <= 7; i++) {
                humidityResult = jsonList.get(i).getDouble("humidity");
                int roundedValue2 = (int) Math.rint(humidityResult * 100);
                humidityList.add(Integer.toString(roundedValue2));
            }

            List<String> conditionNameList = new ArrayList<>();
            String conditionNameResult;
            for(int i = 0; i <= 7; i++) {
                conditionNameResult = updateConditionName(conditionList.get(i));
                conditionNameList.add(conditionNameResult);
            }

            String jsonCurrentCondition = jsonObject.getJSONObject("currently").getString("icon");
            double jsonCurrentHumidity = jsonObject.getJSONObject("currently").getDouble("humidity");
            double jsonCurrentTemperature = jsonObject.getJSONObject("currently").getDouble("temperature");

            for(int i = 0; i <= 7; i++) {
                weatherData.mCondition.add(conditionList.get(i));
                weatherData.mIconName.add(iconList.get(i));
                weatherData.mTemperature.add(tempList.get(i));
                weatherData.mHumidity.add(humidityList.get(i));
                weatherData.mConditionName.add(conditionNameList.get(i));
            }

            weatherData.mCondition.set(0, jsonCurrentCondition);
            weatherData.mIconName.set(0, updateWeatherIcon(weatherData.mCondition.get(0)));
            int roundedVal = (int) Math.rint(jsonCurrentTemperature);
            weatherData.mTemperature.set(0, Integer.toString(roundedVal));
            int roundedVal2 = (int) Math.rint(jsonCurrentHumidity * 100);
            weatherData.mHumidity.set(0, Integer.toString(roundedVal2));
            weatherData.mConditionName.set(0, updateConditionName(weatherData.mCondition.get(0)));

            weatherData.mSummary = jsonObject.getJSONObject("daily").getString("summary");

            return weatherData;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String updateWeatherIcon(String condition) {

        if (condition.equals("clear-day")) {
            return "sunny2";
        } else if (condition.equals("rain")) {
            return "rain";
        } else if (condition.equals("snow")) {
            return "snow";
        } else if (condition.equals("clear-night")) {
            return "night";
        } else if (condition.equals("sleet")) {
            return "sleet2";
        } else if (condition.equals("wind")) {
            return "windy";
        } else if (condition.equals("fog")) {
            return "fog2";
        } else if (condition.equals("cloudy")) {
            return "overcast2";
        } else if (condition.equals("partly-cloudy-day")) {
            return "cloudy";
        } else if (condition.equals("partly-cloudy-night")) {
            return "partly_cloudy_night2";
        }

        return "dunno";
    }

    private static String updateConditionName(String conditionName) {

        if (conditionName.equals("clear-day")) {
            return "Sunny";
        } else if (conditionName.equals("rain")) {
            return "Rain";
        } else if (conditionName.equals("snow")) {
            return "Snow";
        } else if (conditionName.equals("clear-night")) {
            return "Clear Night";
        } else if (conditionName.equals("sleet")) {
            return "Sleet";
        } else if (conditionName.equals("wind")) {
            return "Windy";
        } else if (conditionName.equals("fog")) {
            return "Fog";
        } else if (conditionName.equals("cloudy")) {
            return "Cloudy";
        } else if (conditionName.equals("partly-cloudy-day")) {
            return "Partly Cloudy";
        } else if (conditionName.equals("partly-cloudy-night")) {
            return "Partly Cloudy";
        }

        return "Unknown";
    }

    // TODO: Create getter methods for icon, temperature, and humidity:

    public String getIconName(int dayPosition) {
        return mIconName.get(dayPosition);
    }

    public String getTemperature(int dayPosition) {
        return mTemperature.get(dayPosition) + "˚F";
    }

    public String getHumidity(int dayPosition) {
        return "Humidity = " + mHumidity.get(dayPosition) + "%";
    }

    public String getConditionName(int dayPosition) {
        return mConditionName.get(dayPosition);
    }

    public String getSummary() {
        return mSummary;
    }
}