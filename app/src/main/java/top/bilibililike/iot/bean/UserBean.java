package top.bilibililike.iot.bean;

import org.litepal.crud.LitePalSupport;

public class UserBean{


    /**
     * code : 200
     * message : success
     * data : {"faceid":"0","username":"钟伟民","userid":"2017213074"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends LitePalSupport {

        /**
         * faceid : 0
         * username : 钟伟民
         * userid : 2017213074
         */

        private String faceid;
        private String username;
        private String userid;

        public String getFaceid() {
            return faceid;
        }

        public void setFaceid(String faceid) {
            this.faceid = faceid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
