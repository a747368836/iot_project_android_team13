package top.bilibililike.iot.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

public class UserBean{


    /**
     * code : 200
     * message : success
     * data : {"avatar":"https://i2.hdslb.com/bfs/face/699cdf42d9de9e6e354589d59282aee18469ed7f.jpg@72w_72h.webp","nickname":"管理员"}
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


    public static class DataBean extends LitePalSupport implements Parcelable  {
        /**
         * avatar : https://i2.hdslb.com/bfs/face/699cdf42d9de9e6e354589d59282aee18469ed7f.jpg@72w_72h.webp
         * nickname : 管理员
         */

        private String avatar;
        private String nickname;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.avatar);
            dest.writeString(this.nickname);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.avatar = in.readString();
            this.nickname = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }


}
