package pnt.co.edu.movelo;

import android.content.Intent;
import android.service.autofill.SaveCallback;

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
public class IngresoTest {

    @Rule
    public ActivityScenarioRule<Ingreso> mActivityRule=
            new ActivityScenarioRule<>(Ingreso.class);

    @Test
    public void ingreso(){
        onView(withId(R.id.IngresoEmail))
                .perform(typeText("rrandymiller@gmail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.PasswordIngreso))
                .perform(typeText("Randy123&a"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.IngresoApp))
                .perform(click());

        onView(withId(R.id.IngresoEmail)).check(matches(withText("rrandymiller@gmail.com")));
        onView(withId(R.id.PasswordIngreso)).check(matches(withText("Randy123&a")));
        onView(withId(R.id.mensajeIngreso)).check(matches(withText("Biciusuario")));
    }

}
