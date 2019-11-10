package top.bilibililike.iot.bean;

import java.util.List;

public class DeviceBean extends DeviceBaseBean {

    /**
     * code : 200
     * message : success
     * data : [{"id":"1","object":"light01号","type":"light","stamp":"1572771737"},{"id":"2","object":"light02号","type":"light","stamp":"1572773070"},{"id":"4","object":"三号灯","type":"light","stamp":"1572774229"},{"id":"5","object":"三号灯","type":"light","stamp":"1572776164"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * object : light01号
         * type : light
         * stamp : 1572771737
         */

        private String id;
        private String object;
        private String type;
        private String stamp;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStamp() {
            return stamp;
        }

        public void setStamp(String stamp) {
            this.stamp = stamp;
        }
    }
}
