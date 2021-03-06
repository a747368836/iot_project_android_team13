package top.bilibililike.iot.http;



import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import top.bilibililike.iot.bean.UserBean;

public interface LoginService {


    @GET("/iot/login/")
    Observable<UserBean> login(@Query("username") String username, @Query("password") String password);
}
