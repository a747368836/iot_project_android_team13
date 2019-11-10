package top.bilibililike.iot.utils;

public interface ControlCallback {
    void ledTwinkle(String object);

    void ledChange(String object);

    void currentControl(String order);

    void fanControl(int id,String order);


}
