package top.bilibililike.iot.http;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import top.bilibililike.iot.bean.ClimateBean;
import top.bilibililike.iot.bean.DeviceBean;
import top.bilibililike.iot.bean.PostBackBean;

public interface DeviceService {

    @GET("/iot/device/index.php")
    Observable<DeviceBean> getDevices();

  /*  @POST
    @FormUrlEncoded
    Observable<PostBackBean> post(@Field("mode") String mode,
                                  @Field("object") String object,
                                  @Field("type") String type,
                                  @Field("stamp") String stamp
                                  );*/
    @GET("/iot/index.php")
    Observable<ClimateBean> getClimate();

    @POST("/iot/cache/index.php")
    @FormUrlEncoded
    Observable<PostBackBean> postCache(@Field("mode") String mode,
                                        @Field("object") String object,
                                        @Field("order") String order
                                        );





}
