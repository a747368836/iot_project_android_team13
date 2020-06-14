package top.bilibililike.iot.http;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import top.bilibililike.iot.bean.PostBackBean;
import top.bilibililike.iot.bean.ReportResultBean;

public interface ReportService {
    @FormUrlEncoded
    @POST("/phone/")
    Observable<PostBackBean> report(
            @Field("userid") String userid
            , @Field("username") String username
            ,@Field("time") String time
            ,@Field("issick") boolean isSick
            ,@Field("address") String address
    );

    @GET("/phone/index.php")
    Observable<ReportResultBean> getReportInfo(
            @Query("userid") String userId
    );
}
