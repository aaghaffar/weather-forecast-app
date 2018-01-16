package thealigproject.dexcomchallenge;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by Ali on 1/15/18.
 */
public class WeatherControllerTest {

    @Rule
    public ActivityTestRule<WeatherController> mActivityTestRule = new ActivityTestRule<WeatherController>(WeatherController.class);

    private WeatherController mWeatherController = null;

    @Before
    public void setUp() throws Exception {

        mWeatherController = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        View view = mWeatherController.findViewById(R.id.dayList); //dayList is in weather_controller_layout

        assertNotNull(view);

        assertNull(mWeatherController.findViewById(R.id.tempTV)); //tempTV not in weather_controller_layout
    }

    @After
    public void tearDown() throws Exception {

        mWeatherController = null;
    }

}