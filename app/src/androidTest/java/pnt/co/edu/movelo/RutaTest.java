package pnt.co.edu.movelo;

import android.content.Intent;
import android.service.autofill.SaveCallback;
import android.view.KeyEvent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;

import static androidx.test.core.app.ActivityScenario.launch;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RutaTest {


    @Rule
    public ActivityScenarioRule<MainPage> mActivityRule=
            new ActivityScenarioRule<>(MainPage.class);

    @Test
    public void crearRuta(){
        onView(withId(R.id.autocomplete_fragment))
                .perform(typeText("Gran estacion"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.autocomplete_fragment))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.endRoute)).check(matches(isDisplayed()));
    }

}
