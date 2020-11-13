package pnt.co.edu.movelo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistroBiciusuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_biciusuario);


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chilling-castle-88137.herokuapp.com/")
                .build();

        final HerokuService service = retrofit.create(HerokuService.class);
        final TextView textView = findViewById(R.id.mensaje);
        final EditText pNombre=findViewById(R.id.nombreBiciusuario);
        final EditText pEmail=findViewById(R.id.emailBiciusuario);
        final EditText pPassword=findViewById(R.id.passwordBiciusuario);
        final EditText pPasswordConfirm=findViewById(R.id.conpasswordBiciusuario);


        Button boton = findViewById(R.id.registrarBiciusuario);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre=pNombre.getText().toString();
                String email=pEmail.getText().toString();
                SharedPreferences prefs=getSharedPreferences("Shared_email",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= prefs.edit();
                editor.putString("email",email);
                editor.apply();
                String password=pPassword.getText().toString();
                String conPassword=pPasswordConfirm.getText().toString();
                Context context= getApplicationContext();
                Context context1= getApplicationContext();
                Context context2= getApplicationContext();
                CharSequence text="Ingrese todos los datos";
                CharSequence text1="Bienvenido";
                CharSequence text2="El estudiante ya existe";
                int duration= Toast.LENGTH_SHORT;
                final Toast toast=Toast.makeText(context, text, duration);
                final Toast toast1=Toast.makeText(context1, text1, duration);
                final Toast toast2=Toast.makeText(context2, text2, duration);

                Call<ResponseBody> call = service.registroBiciusuario(nombre, email, password, conPassword);
                call.enqueue(new Callback<ResponseBody>(){
                @Override
                public void onResponse (Call < ResponseBody > call, Response < ResponseBody > response){

                    try {
                        String respuesta=response.body().string();
                        textView.setText(respuesta);
                        if(respuesta.equals("Registrado correctamente!")){
                            Intent p=new Intent(RegistroBiciusuario.this,MainPage.class);
                            startActivity(p);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        textView.setText(e.getMessage());
                    }
                }
                @Override
                public void onFailure (Call < ResponseBody > call, Throwable t){
                    t.printStackTrace();
                    textView.setText(t.getMessage());
                }
            });
        }
    });
}
}
