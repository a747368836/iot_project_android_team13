package top.bilibililike.iot.widget;


import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import top.bilibililike.iot.bean.UserBean;
import top.bilibililike.iot.bean.UserBean.DataBean;

public class TestManager {

    public static void main(String[] args) {
        String userName = "llb";
        int userId = 201711111;
        DataBean dataBean = new DataBean();
        dataBean.setUserid(userName);
        dataBean.setUsername("userName");
        dataBean.setFaceid("FaceId");
        UserBean userBean = new UserBean();
        userBean.setCode(200);
        userBean.setMessage("Success");
        userBean.setData(dataBean);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(userBean);
        System.out.println(jsonStr);

    }
}
