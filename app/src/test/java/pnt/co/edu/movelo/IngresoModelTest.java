package pnt.co.edu.movelo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IngresoModelTest {

    @Mock
    private Login.Presenter presenter;

    private IngresoModel model;

    @Before
    public  void setup() throws Exception{
        model=new IngresoModel(presenter);
    }

    @Test
    public void exitoUsuarioValido() throws Exception{
        String res=model.validarUser("rrandymiller@gmail.com","Randy123&a");
        assertEquals("Biciusuario",res);
        verify(presenter).usuarioValido();
    }

    @Test
    public void fracasoUsuario() throws Exception{
        model.validarUser("rrandyrodi@gmail.com","Randy123&a");
        verify(presenter).error();
    }

}