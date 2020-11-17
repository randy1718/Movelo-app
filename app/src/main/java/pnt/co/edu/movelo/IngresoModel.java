package pnt.co.edu.movelo;

public class IngresoModel implements Login.Model{

    private Login.Presenter presenter;

    public IngresoModel(Login.Presenter presenter){
        this.presenter=presenter;
    }

    @Override
    public String validarUser(String user, String password) {
        Ingreso ingreso=new Ingreso();
        ingreso.validarUsuario(user,password);
        String respuesta=ingreso.getRes();
        if(user.equals("rrandymiller@gmail.com") && password.equals("Randy123&a") && respuesta.equals("Biciusuario")){
            presenter.usuarioValido();
        }else{
            presenter.error();
        }
        /*if(user.equals("rrandymiller@gmail.com") && password.equals("Randy123&a")){
            presenter.usuarioValido();
        }else{
            presenter.error();
        }*/
        return respuesta;
    }
}
