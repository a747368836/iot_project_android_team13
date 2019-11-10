package top.bilibililike.iot.utils;

import android.view.MotionEvent;
import android.view.View;

public class OnDoubleClickListener implements View.OnTouchListener{

    private int count = 0;//点击次数
    private long firstClick = 0;//第一次点击时间
    private long secondClick = 0;//第二次点击时间
    /**
     * 两次点击时间间隔，单位毫秒
     */
    private final int totalTime = 500;
    /**
     * 自定义回调接口
     */
    private DoubleClickCallback mCallback;

    public interface DoubleClickCallback {
        void onDoubleClick();

        void onLongClick();
    }
    public OnDoubleClickListener(DoubleClickCallback callback) {
        super();
        this.mCallback = callback;
    }

    /**
     * 触摸事件处理
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {//按下
            count++;
            if (1 == count) {
                firstClick = System.currentTimeMillis();//记录第一次点击时间
            } else if (2 == count) {
                secondClick = System.currentTimeMillis();//记录第二次点击时间
                if (secondClick - firstClick < totalTime) {//判断二次点击时间间隔是否在设定的间隔时间之内
                    if (mCallback != null) {
                        mCallback.onDoubleClick();
                    }
                    count = 0;
                    firstClick = 0;
                } else {
                    firstClick = secondClick;
                    count = 1;
                    secondClick = 0;
                    return false;
                }
                secondClick = 0;
            }
        }

        if (MotionEvent.ACTION_UP == event.getAction()){
            if (count == 1 && System.currentTimeMillis() - firstClick > 1000){
                mCallback.onLongClick();
                count = 0;
                return true;
            }
        }
        return true;
    }
}
