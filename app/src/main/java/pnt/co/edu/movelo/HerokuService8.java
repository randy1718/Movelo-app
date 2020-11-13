package pnt.co.edu.movelo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface HerokuService8 {
    @POST("DarHuellaCarbono")
    @FormUrlEncoded
    Call<ResponseBody> darHuellaCarbono(@Field("email") String email);
}
