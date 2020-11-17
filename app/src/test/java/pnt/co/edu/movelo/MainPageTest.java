package pnt.co.edu.movelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/*
 *@see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

 class MainPageTest {

    @Test
    void actualizarUbicacion() {
        MainPage mainpage=new MainPage();
        mainpage.actualizarUbicacion();
        assertEquals("1","1");
    }

    @Test
    void onMapReady() {
    }

    @Test
    void iniciarRuta() {
    }

    @Test
    void getDistancia() {
    }

    @Test
    void getDistance() {
    }
}