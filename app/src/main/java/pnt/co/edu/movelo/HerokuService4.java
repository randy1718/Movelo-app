package pnt.co.edu.movelo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface HerokuService4 {

    @POST("AgregarPuntos")
    @FormUrlEncoded
    Call<ResponseBody> agregarPuntos(@Field("distanciaRecorrida")Double distanciaRecorrida,@Field("email")String email);

}
