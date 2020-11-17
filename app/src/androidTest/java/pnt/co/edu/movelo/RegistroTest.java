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
public class RegistroTest {

   @Rule
    public ActivityScenarioRule<RegistroBiciusuario> mActivityRule=
            new ActivityScenarioRule<>(RegistroBiciusuario.class);


    /*@Before
    public void launchActivity(){
    ActivityScenario<RegistroBiciusuario> scenarioRegistro=ActivityScenario.launch(RegistroBiciusuario.class);
        scenarioRegistro.moveToState(Lifecycle.State.RESUMED);

    }*/

    @Test
    public void Registro() throws AssertionError{
        /*try(ActivityScenario<RegistroBiciusuario>scenario=ActivityScenario.launch(RegistroBiciusuario.class)){
            scenario.moveToState(Lifecycle.State.CREATED);
        }*/
        //ActivityScenario.launch(RegistroBiciusuario.class);
        onView(withId(R.id.nombreBiciusuario))
                .perform(typeText("Randy Miller Rojas Diaz"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.emailBiciusuario))
                .perform(typeText("rrandymiller@gmail.com"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.passwordBiciusuario))
                .perform(typeText("Randy123&a"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.conpasswordBiciusuario))
                .perform(typeText("Randy123&a"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.registrarBiciusuario))
                .perform(click());

        onView(withId(R.id.nombreBiciusuario)).check(matches(withText("Randy Miller Rojas Diaz")));
    }

}
