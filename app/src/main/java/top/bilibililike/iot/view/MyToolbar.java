package top.bilibililike.iot.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toolbar;

public class MyToolbar extends Toolbar {

    public MyToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyToolbar(Context context) {
        super(context);
    }

    //0展开1折叠2中间
    private int status = 0;

    public void setStatus(int status){
        this.status = status;
    }



    @Override
    public CharSequence getTitle() {
        if (status == 0){
            return "";
        }else {
            return super.getTitle();
        }

    }
}
