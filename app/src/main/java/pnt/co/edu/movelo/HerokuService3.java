package pnt.co.edu.movelo;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface HerokuService3 {

    @POST("GuardarRuta")
    @FormUrlEncoded
    Call<ResponseBody> guardarRuta(@Field("ruta") ArrayList<Punto> ruta, @Field("email")String email);

}
