package top.bilibililike.iot.utils;

public class Status {
    private static boolean isLogin = false;

    public static boolean getLoginStatus(){
        return isLogin;
    }

    public static synchronized void setIsLogin(boolean login){
        isLogin = login;
    }
}
