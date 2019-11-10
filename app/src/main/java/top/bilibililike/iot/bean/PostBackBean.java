package top.bilibililike.iot.bean;

public class PostBackBean {

    /**
     * code : 200
     * message : device 三号灯 registered successful
     */

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
