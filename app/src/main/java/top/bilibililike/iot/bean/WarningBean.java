package top.bilibililike.iot.bean;

import java.util.List;

public class WarningBean {

    /**
     * code : 200
     * message : success
     * data : {"absentList":[["2017213073","周嘉晖"],["2017213074","钟伟民"],["2017213075","郝书逸"],["2017213076","郑武杰"],["2017213077","边策"],["2017213078","胡凌侨"],["2017213080","王天晖"],["2017213081","廖廷春"],["2017213082","毛梓蒙"]],"sickList":[]}
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

    public static class DataBean {

        private List<List<String>> absentList;
        private List<List<String>> sickList;

        public List<List<String>> getAbsentList() {
            return absentList;
        }

        public void setAbsentList(List<List<String>> absentList) {
            this.absentList = absentList;
        }

        public List<List<String>> getSickList() {
            return sickList;
        }

        public void setSickList(List<List<String>> sickList) {
            this.sickList = sickList;
        }
    }
}
