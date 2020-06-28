package top.bilibililike.iot.utils;

public class NotificationBindHelper {
    public static volatile NotificationBindHelper instance = null;

    private int notificationId;
    private BindCallback callback;

    public static final String CHANNEL_ID = "114";
    public static final int NOTIFICATION_ID = 514;

    public static NotificationBindHelper getInstance() {
        if (instance == null) {
            synchronized (NotificationBindHelper.class) {
                if (instance == null) {
                    instance = new NotificationBindHelper();
                }
            }
        }
        return instance;
    }

    public void bindNotification(int id, BindCallback callback) {
        this.callback = callback;
        this.notificationId = id;
    }

    public void onClear() {
        if (callback != null) {
            callback.onCleared();
        }
    }

    public void onClick() {
        if (callback != null) {
            callback.onClick();
        }
    }


    public interface BindCallback {
        void onClick();

        void onCleared();
    }
}
