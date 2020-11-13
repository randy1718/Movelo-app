package pnt.co.edu.movelo;



import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.graphics.Color;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.intellij.lang.annotations.JdkConstants;

import java.util.ArrayList;

public class TreeMarket   extends AppCompatActivity {

    private int puntos;
    BottomNavigationView opciones;
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://chilling-castle-88137.herokuapp.com/")
            .build();

    final HerokuService5 service = retrofit.create(HerokuService5.class);
    final HerokuService6 servicio = retrofit.create(HerokuService6.class);

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TextView tv1 = (TextView) findViewById(R.id.txt5);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.tree_market);
            final TextView tv1 = (TextView) findViewById(R.id.txt5);
            /*Explode explode=new Explode();
            explode.setDuration(500);
            getWindow().setEnterTransition(explode);
            getWindow().setExitTransition(explode);*/
            SharedPreferences prefs=getSharedPreferences("Shared_email", Context.MODE_PRIVATE);
            final String correo=prefs.getString("email","");
            Call<ResponseBody> llamado=service.darPuntos(correo);
            llamado.enqueue(new Callback<ResponseBody>(){
                @Override
                public void onResponse (Call < ResponseBody > call, Response< ResponseBody > response){

                    try {
                        String respuesta=response.body().string();
                        puntos=Integer.parseInt(respuesta);
                        tv1.setText("Mis Puntos: "+puntos);
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


            opciones=findViewById(R.id.msiOpcionesArboles);
            opciones.setSelectedItemId(R.id.arboles);
            opciones.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.arboles:
                            break;
                        case R.id.homePage:
                            Intent c=new Intent(TreeMarket.this,MainPage.class);
                            SharedPreferences prefs=getSharedPreferences("Place",Context.MODE_PRIVATE);
                            final String lugar=prefs.getString("place","");
                            c.putExtra("place",lugar);
                            SharedPreferences pref1=getSharedPreferences("ubicacionlat",Context.MODE_PRIVATE);
                            SharedPreferences pref2=getSharedPreferences("ubicacionlon",Context.MODE_PRIVATE);
                            final String latitud=pref1.getString("ubicacionLat","");
                            final String longitud=pref2.getString("ubicacionLon","");
                            c.putExtra("lat",latitud);
                            c.putExtra("lon",longitud);
                            Log.d("Nombre place ",lugar);
                            finish();
                            startActivity(c, ActivityOptions.makeSceneTransitionAnimation(TreeMarket.this).toBundle());

                            break;
                        case R.id.cuenta:
                            Intent d=new Intent(TreeMarket.this,AccountPage.class);
                            finish();
                            startActivity(d, ActivityOptions.makeSceneTransitionAnimation(TreeMarket.this).toBundle());
                            break;

                    }
                    return true;
                }
            });

            RelativeLayout ar1 = findViewById(R.id.image_btn);
            RelativeLayout ar2 = findViewById(R.id.image_btn2);
            RelativeLayout ar3 = findViewById(R.id.image_btn3);
            RelativeLayout ar4 = findViewById(R.id.image_btn4);

            ar1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mostrar(30,"Pinus Radiata", 38);
                }
            });

            ar2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mostrar(20,"Pinus Sylvestris", 3.33);
                }
            });

            ar3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mostrar(23,"Plátano de sombra", 21.66);
                }
            });

            ar4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mostrar(10,"Encina", 5);
                }
            });



        }



        private void mostrar(final int puntosarbol, final String arbol, final double ahorro) {

            if (puntos >= puntosarbol) {

                final TextView tv1 = (TextView) findViewById(R.id.txt5);
                new SweetAlertDialog(TreeMarket.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("¿Seguro que quieres comprar este árbol?")
                        .setContentText("Se te descontarán " + puntosarbol + " puntos.")
                        .setConfirmText("¡Lo compro!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                /*TextView text=sDialog.findViewById(R.id.content_text);
                                Typeface typeface= ResourcesCompat.getFont(TreeMarket.this,R.font.poppins_semibold);
                                text.setTypeface(typeface);*/
                                sDialog
                                        .setTitleText("Árbol comprado ☺️")
                                        .setContentText("Has comprado exitosamente un " + arbol + ", con lo cual ahorrarás " + ahorro + " Kg de CO2 por año. \uD83C\uDF31")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                            }
                                        })
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                SharedPreferences prefs=getSharedPreferences("Shared_email", Context.MODE_PRIVATE);
                                final String correo=prefs.getString("email","");

                                Call<ResponseBody> llamado=servicio.comprarArbol(arbol,ahorro,puntosarbol,correo);
                                llamado.enqueue(new Callback<ResponseBody>(){
                                    @Override
                                    public void onResponse (Call < ResponseBody > call, Response< ResponseBody > response){

                                        try {
                                            String respuesta=response.body().string();
                                            Toast.makeText(getApplicationContext(), "Mensaje: "+respuesta +" - "+ correo, Toast.LENGTH_LONG).show();
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

                                Call<ResponseBody> cally=service.darPuntos(correo);
                                cally.enqueue(new Callback<ResponseBody>(){
                                    @Override
                                    public void onResponse (Call < ResponseBody > call, Response< ResponseBody > response){

                                        try {
                                            String respuesta=response.body().string();
                                            puntos=Integer.parseInt(respuesta);
                                            tv1.setText("Mis Puntos: "+puntos);
                                            Toast.makeText(getApplicationContext(), "Mensaje: "+respuesta +" - "+ correo, Toast.LENGTH_LONG).show();
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

                            }
                        })
                        .show();

            } else {

                new SweetAlertDialog(TreeMarket.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Parece que no tienes puntos suficientes para comprar este árbol, sigue pedaleando. ")
                        .show();

            }
        }

}
