package pnt.co.edu.movelo;

public class IngresoPresenter implements Login.Presenter{

    private Login.View view;
    private Login.Model model;

    public IngresoPresenter(Login.View view){
        this.view=view;
        model=new IngresoModel(this);
    }
    @Override
    public String validarUser(String username, String password) {
        if(view!=null){
            model.validarUser(username,password);
        }
        return "";
    }

    @Override
    public void usuarioValido() {
        if(view!=null){
            view.usuarioValido();
        }
    }

    @Override
    public void error() {
        if(view!=null){
            view.error();
        }
    }
}
