package thealigproject.dexcomchallenge;

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
    //Numbers represent day of week (Sunday = 0, Monday = 1, etc.)
    //Inefficient solution, but the best I could come up with at the time.
    //It pains me to write repetitive variables, trust me...
    private String mTemperature;
    private String mTemperature1;
    private String mTemperature2;
    private String mTemperature3;
    private String mTemperature4;
    private String mTemperature5;
    private String mTemperature6;
    private String mCondition;
    private String mCondition1;
    private String mCondition2;
    private String mCondition3;
    private String mCondition4;
    private String mCondition5;
    private String mCondition6;
    private String mHumidity;
    private String mHumidity1;
    private String mHumidity2;
    private String mHumidity3;
    private String mHumidity4;
    private String mHumidity5;
    private String mHumidity6;
    private String mIconName;
    private String mIconName1;
    private String mIconName2;
    private String mIconName3;
    private String mIconName4;
    private String mIconName5;
    private String mIconName6;

    // TODO: Create a WeatherDataModel from a JSON:
    public static WeatherDataModel fromJson(JSONObject jsonObject) {
        try {

            WeatherDataModel weatherData = new WeatherDataModel();

            List<JSONObject> jsonArray = new ArrayList<JSONObject>();
            JSONObject jsonResult;
            for(int i = 0; i <= 6 ; i++) {
                    jsonResult = jsonObject.getJSONObject("daily").getJSONArray("data")
                        .getJSONObject(i);
                    jsonArray.add(jsonResult);
            }

            //Condition for each day of the week
            weatherData.mCondition = jsonArray.get(0).getString("icon");
            weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);
            weatherData.mCondition1 = jsonArray.get(1).getString("icon");
            weatherData.mIconName1 = updateWeatherIcon(weatherData.mCondition1);
            weatherData.mCondition2 = jsonArray.get(2).getString("icon");
            weatherData.mIconName2 = updateWeatherIcon(weatherData.mCondition2);
            weatherData.mCondition3 = jsonArray.get(3).getString("icon");
            weatherData.mIconName3 = updateWeatherIcon(weatherData.mCondition3);
            weatherData.mCondition4 = jsonArray.get(4).getString("icon");
            weatherData.mIconName4 = updateWeatherIcon(weatherData.mCondition4);
            weatherData.mCondition5 = jsonArray.get(5).getString("icon");
            weatherData.mIconName5 = updateWeatherIcon(weatherData.mCondition5);
            weatherData.mCondition6 = jsonArray.get(6).getString("icon");
            weatherData.mIconName6 = updateWeatherIcon(weatherData.mCondition6);

            //Humidity for each day of the week
            double humidityResult = jsonArray.get(0).getDouble("humidity");
            weatherData.mHumidity = Double.toString(humidityResult);
            double humidityResult1 = jsonArray.get(1).getDouble("humidity");
            weatherData.mHumidity1 = Double.toString(humidityResult1);
            double humidityResult2 = jsonArray.get(2).getDouble("humidity");
            weatherData.mHumidity2 = Double.toString(humidityResult2);
            double humidityResult3 = jsonArray.get(3).getDouble("humidity");
            weatherData.mHumidity3 = Double.toString(humidityResult3);
            double humidityResult4 = jsonArray.get(4).getDouble("humidity");
            weatherData.mHumidity4 = Double.toString(humidityResult4);
            double humidityResult5 = jsonArray.get(5).getDouble("humidity");
            weatherData.mHumidity5 = Double.toString(humidityResult5);
            double humidityResult6 = jsonArray.get(6).getDouble("humidity");
            weatherData.mHumidity6 = Double.toString(humidityResult6);

            //Temperature for each day of the week
            double tempResult = jsonArray.get(0).getDouble("temperatureMax");
            int roundedValue = (int) Math.rint(tempResult);
            weatherData.mTemperature = Integer.toString(roundedValue);
            double tempResult1 = jsonArray.get(1).getDouble("temperatureMax");
            int roundedValue1 = (int) Math.rint(tempResult1);
            weatherData.mTemperature1 = Integer.toString(roundedValue1);
            double tempResult2 = jsonArray.get(2).getDouble("temperatureMax");
            int roundedValue2 = (int) Math.rint(tempResult2);
            weatherData.mTemperature2 = Integer.toString(roundedValue2);
            double tempResult3 = jsonArray.get(3).getDouble("temperatureMax");
            int roundedValue3 = (int) Math.rint(tempResult3);
            weatherData.mTemperature3 = Integer.toString(roundedValue3);
            double tempResult4 = jsonArray.get(4).getDouble("temperatureMax");
            int roundedValue4 = (int) Math.rint(tempResult4);
            weatherData.mTemperature4 = Integer.toString(roundedValue4);
            double tempResult5 = jsonArray.get(5).getDouble("temperatureMax");
            int roundedValue5 = (int) Math.rint(tempResult5);
            weatherData.mTemperature5 = Integer.toString(roundedValue5);
            double tempResult6 = jsonArray.get(6).getDouble("temperatureMax");
            int roundedValue6 = (int) Math.rint(tempResult6);
            weatherData.mTemperature6 = Integer.toString(roundedValue6);

            Calendar cal = Calendar.getInstance();
            int today = cal.get(Calendar.DAY_OF_WEEK);
            String todayStr = String.valueOf(today);

            if(todayStr.equals("1")) {
                weatherData.mCondition = jsonObject.getJSONObject("currently").getString("icon");
                weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);
                double humidityR = jsonObject.getJSONObject("currently").getDouble("humidity");
                weatherData.mHumidity = Double.toString(humidityR);
                double tempR = jsonObject.getJSONObject("currently").getDouble("temperature");
                int roundedVal = (int) Math.rint(tempR);
                weatherData.mTemperature = Integer.toString(roundedVal);
            }
            if(todayStr.equals("2")) {
                weatherData.mCondition = jsonObject.getJSONObject("currently").getString("icon");
                weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);
                double humidityR = jsonObject.getJSONObject("currently").getDouble("humidity");
                weatherData.mHumidity = Double.toString(humidityR);
                double tempR = jsonObject.getJSONObject("currently").getDouble("temperature");
                int roundedVal = (int) Math.rint(tempR);
                weatherData.mTemperature = Integer.toString(roundedVal);
            }
            if(todayStr.equals("3")) {
                weatherData.mCondition = jsonObject.getJSONObject("currently").getString("icon");
                weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);
                double humidityR = jsonObject.getJSONObject("currently").getDouble("humidity");
                weatherData.mHumidity = Double.toString(humidityR);
                double tempR = jsonObject.getJSONObject("currently").getDouble("temperature");
                int roundedVal = (int) Math.rint(tempR);
                weatherData.mTemperature = Integer.toString(roundedVal);
            }
            if(todayStr.equals("4")) {
                weatherData.mCondition = jsonObject.getJSONObject("currently").getString("icon");
                weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);
                double humidityR = jsonObject.getJSONObject("currently").getDouble("humidity");
                weatherData.mHumidity = Double.toString(humidityR);
                double tempR = jsonObject.getJSONObject("currently").getDouble("temperature");
                int roundedVal = (int) Math.rint(tempR);
                weatherData.mTemperature = Integer.toString(roundedVal);
            }
            if(todayStr.equals("5")) {
                weatherData.mCondition = jsonObject.getJSONObject("currently").getString("icon");
                weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);
                double humidityR = jsonObject.getJSONObject("currently").getDouble("humidity");
                weatherData.mHumidity = Double.toString(humidityR);
                double tempR = jsonObject.getJSONObject("currently").getDouble("temperature");
                int roundedVal = (int) Math.rint(tempR);
                weatherData.mTemperature = Integer.toString(roundedVal);
            }
            if(todayStr.equals("6")) {
                weatherData.mCondition = jsonObject.getJSONObject("currently").getString("icon");
                weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);
                double humidityR = jsonObject.getJSONObject("currently").getDouble("humidity");
                weatherData.mHumidity = Double.toString(humidityR);
                double tempR = jsonObject.getJSONObject("currently").getDouble("temperature");
                int roundedVal = (int) Math.rint(tempR);
                weatherData.mTemperature = Integer.toString(roundedVal);
            }
            if(todayStr.equals("7")) {
                weatherData.mCondition = jsonObject.getJSONObject("currently").getString("icon");
                weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);
                double humidityR = jsonObject.getJSONObject("currently").getDouble("humidity");
                weatherData.mHumidity = Double.toString(humidityR);
                double tempR = jsonObject.getJSONObject("currently").getDouble("temperature");
                int roundedVal = (int) Math.rint(tempR);
                weatherData.mTemperature = Integer.toString(roundedVal);
            }


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

    // TODO: Create getter methods for temperature, humidity, and icon name:

    //Temperature

    public String getTemperature(String day) {
        if (day.equals("Sunday")) {
            return mTemperature + "°F ";
        } else if (day.equals("Monday")) {
            return mTemperature1 + "°F ";
        } else if (day.equals("Tuesday")) {
            return mTemperature2 + "°F ";
        } else if (day.equals("Wednesday")) {
            return mTemperature3 + "°F ";
        } else if (day.equals("Thursday")) {
            return mTemperature4 + "°F ";
        } else if (day.equals("Friday")) {
            return mTemperature5 + "°F ";
        } else if (day.equals("Saturday")) {
            return mTemperature6 + "°F ";
        } else {
            return null;
        }
    }

    public String getIconName(String day) {
        if (day.equals("Sunday")) {
            return mIconName;
        } else if (day.equals("Monday")) {
            return mIconName1;
        } else if (day.equals("Tuesday")) {
            return mIconName2;
        } else if (day.equals("Wednesday")) {
            return mIconName3;
        } else if (day.equals("Thursday")) {
            return mIconName4;
        } else if (day.equals("Friday")) {
            return mIconName5;
        } else if (day.equals("Saturday")) {
            return mIconName6;
        } else {
            return null;
        }
    }

    public String getHumidity(String day) {
        if (day.equals("Sunday")) {
            return "Humidity = " + mHumidity;
        } else if (day.equals("Monday")) {
            return "Humidity = " + mHumidity1;
        } else if (day.equals("Tuesday")) {
            return "Humidity = " + mHumidity2;
        } else if (day.equals("Wednesday")) {
            return "Humidity = " + mHumidity3;
        } else if (day.equals("Thursday")) {
            return "Humidity = " + mHumidity4;
        } else if (day.equals("Friday")) {
            return "Humidity = " + mHumidity5;
        } else if (day.equals("Saturday")) {
            return "Humidity = " + mHumidity6;
        } else {
            return null;
        }
    }
}