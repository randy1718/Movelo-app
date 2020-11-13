package pnt.co.edu.movelo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface HerokuService6 {

    @POST("ComprarArbol")
    @FormUrlEncoded
    Call<ResponseBody> comprarArbol(@Field("nameArbol")String name,@Field("huellaCarbono")Double huella,@Field("points")int puntos, @Field("email")String email);

}
