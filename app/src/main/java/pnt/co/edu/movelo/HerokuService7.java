package pnt.co.edu.movelo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface HerokuService7 {

    @POST("DarArboles")
    @FormUrlEncoded
    Call<ResponseBody> darArboles(@Field("email") String email);
}
