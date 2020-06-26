package top.bilibililike.iot.bean;

import java.util.List;

public class ReportHistoryBean {


    /**
     * code : 200
     * message : success
     * data : [{"username":"钟伟民","temperature":null,"ispic":null,"issick":"true","address":"重庆邮电大学","time":"1592052487984"},{"username":"钟伟民","temperature":null,"ispic":null,"issick":"false","address":"重庆邮电大学","time":"1593013331084"}]
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
         * username : 钟伟民
         * temperature : null
         * ispic : null
         * issick : true
         * address : 重庆邮电大学
         * time : 1592052487984
         */

        private String username;
        private Object temperature;
        private Object ispic;
        private String issick;
        private String address;
        private String time;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Object getTemperature() {
            return temperature;
        }

        public void setTemperature(Object temperature) {
            this.temperature = temperature;
        }

        public Object getIspic() {
            return ispic;
        }

        public void setIspic(Object ispic) {
            this.ispic = ispic;
        }

        public String getIssick() {
            return issick;
        }

        public void setIssick(String issick) {
            this.issick = issick;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
