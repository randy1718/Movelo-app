package pnt.co.edu.movelo;

public interface Login {
    interface View{
        void usuarioValido();
        void error();
        String getUsername();
        String getPassword();
    }
    interface Presenter{
        String validarUser(String username,String password);
        void usuarioValido();
        void error();
    }
    interface Model{
        String validarUser(String user,String password);
    }
}
