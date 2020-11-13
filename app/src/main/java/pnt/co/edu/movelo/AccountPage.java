package pnt.co.edu.movelo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AccountPage extends AppCompatActivity {
    BottomNavigationView opciones;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        /*Explode explode=new Explode();
        explode.setDuration(500);
        getWindow().setEnterTransition(explode);
        getWindow().setExitTransition(explode);*/

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://chilling-castle-88137.herokuapp.com/")
                .build();
        final HerokuService7 service = retrofit.create(HerokuService7.class);
        final HerokuService8 service2 = retrofit.create(HerokuService8.class);
        final HerokuService9 service3 = retrofit.create(HerokuService9.class);



        final TextView tv1 = (TextView) findViewById(R.id.arbolesObtenidos);
        final TextView tv2 = (TextView) findViewById(R.id.aporteAmbiental);
        final TextView tv3 = (TextView) findViewById(R.id.nombreSesionUser);
        SharedPreferences prefs=getSharedPreferences("Shared_email", Context.MODE_PRIVATE);
        final String correo=prefs.getString("email","");
        Call<ResponseBody> llamado=service.darArboles(correo);
        llamado.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse (Call < ResponseBody > call, Response< ResponseBody > response){

                try {
                    String respuesta=response.body().string();
                     tv1.setText(respuesta);
                    //Toast.makeText(getApplicationContext(), "Mensaje: "+respuesta +" - "+ correo, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure (Call < ResponseBody > call, Throwable t){
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Call<ResponseBody> cally=service2.darHuellaCarbono(correo);
        cally.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse (Call < ResponseBody > call, Response< ResponseBody > response){

                try {
                    String respuesta=response.body().string();
                    tv2.setText(respuesta);
                    //Toast.makeText(getApplicationContext(), "Mensaje: "+respuesta +" - "+ correo, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure (Call < ResponseBody > call, Throwable t){
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Call<ResponseBody> call=service3.darNombreUsuario(correo);
        call.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse (Call < ResponseBody > call, Response< ResponseBody > response){

                try {
                    String respuesta=response.body().string();
                    tv3.setText(respuesta.toUpperCase());
                    //Toast.makeText(getApplicationContext(), "Mensaje: "+respuesta +" - "+ correo, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure (Call < ResponseBody > call, Throwable t){
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        opciones=findViewById(R.id.myOptions);
        opciones.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft= fm.beginTransaction();

                switch (item.getItemId()){
                    case R.id.arboles:
                        /*if(fm.findFragmentByTag("tree")==null) {
                            TreeFragment treeFragment = new TreeFragment();
                            ft.replace(R.id.content, treeFragment, "tree").commit();
                        }*/
                        Intent p=new Intent(AccountPage.this,TreeMarket.class);
                        startActivity(p, ActivityOptions.makeSceneTransitionAnimation(AccountPage.this).toBundle());
                        finish();
                        break;
                    case R.id.homePage:
                        Intent h=new Intent(AccountPage.this,MainPage.class);
                        startActivity(h, ActivityOptions.makeSceneTransitionAnimation(AccountPage.this).toBundle());
                        finish();
                        /*if(fm.findFragmentByTag("home")==null) {
                            HomeFragment homeFragment = new HomeFragment(MainPage.this);
                            ft.replace(R.id.content, homeFragment, "home").commit();
                            break;
                        }*/
                    case R.id.cuenta:
                        /*if(fm.findFragmentByTag("account")==null) {
                            AccountFragment accountFragment = new AccountFragment();
                            ft.replace(R.id.content, accountFragment, "account").commit();
                            break;
                        }*/

                }
                return true;
            }
        });
    }



}