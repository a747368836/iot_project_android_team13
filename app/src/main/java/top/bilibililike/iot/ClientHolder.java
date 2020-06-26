package top.bilibililike.iot;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ClientHolder {
    private static Retrofit instance = null;

    public static Retrofit getInstance(){
        if (instance == null){
            synchronized (ClientHolder.class){
                if (instance == null){
                    instance = new Retrofit.Builder()
                            .baseUrl("http://iot.bilibililike.top")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return instance;
    }
}
