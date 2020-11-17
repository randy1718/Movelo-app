package pnt.co.edu.movelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PuntoTest {

    @Test
    void punto() {
        Double latitud=4.8765432;
        Double longitud=-72.912345;
        Punto punto=new Punto(latitud,longitud);
        assertEquals("4.8765432, -72.912345",punto.getLatitud()+", "+punto.getLongitud());
    }

    @Test
    void setLatitud() {
        Double latitud=4.8765432;
        Double longitud=-72.912345;
        Punto punto=new Punto(latitud,longitud);
        punto.setLatitud(4.567767);
        assertEquals("4.567767, -72.912345",punto.getLatitud()+", "+punto.getLongitud());
    }

    @Test
    void setLongitud() {
        Double latitud=4.8765432;
        Double longitud=-72.912345;
        Punto punto=new Punto(latitud,longitud);
        punto.setLongitud(-72.123522);
        assertEquals("4.8765432, -72.123522",punto.getLatitud()+", "+punto.getLongitud());
    }
}