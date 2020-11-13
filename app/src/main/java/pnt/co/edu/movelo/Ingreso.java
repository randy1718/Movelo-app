package pnt.co.edu.movelo;


import android.annotation.SuppressLint;
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

public class Ingreso extends AppCompatActivity {

    public static String emailGuardado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso);
        emailGuardado="";

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chilling-castle-88137.herokuapp.com/")
                .build();

        final HerokuService2 service = retrofit.create(HerokuService2.class);
        final TextView textView = findViewById(R.id.mensajeIngreso);
        final EditText pEmail=findViewById(R.id.IngresoEmail);
        final EditText pPassword=findViewById(R.id.PasswordIngreso);

        Button boton = findViewById(R.id.IngresoApp);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=pEmail.getText().toString();
                emailGuardado=email;
                SharedPreferences prefs=getSharedPreferences("Shared_email",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= prefs.edit();
                editor.putString("email",email);
                editor.apply();
                String password=pPassword.getText().toString();
                SharedPreferences pref=getSharedPreferences("Place",Context.MODE_PRIVATE);
                SharedPreferences.Editor edit= pref.edit();
                edit.putString("place",null);
                edit.apply();
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

                Call<ResponseBody> call = service.ingreso(email, password);
                call.enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse (Call < ResponseBody > call, Response< ResponseBody > response){

                        try {

                            String respuesta=response.body().string();
                            if(respuesta.equals("Biciusuario")){
                                Intent p=new Intent(Ingreso.this,MainPage.class);
                                startActivity(p);
                            }
                            textView.setText(respuesta);


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
