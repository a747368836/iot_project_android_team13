package top.bilibililike.iot.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class BgView extends View {

    private int width;
    private int height;
    private Paint paint;

    private int widthCount;

    private Path path;

    public BgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.parseColor("#36EEB9"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        widthCount = dm.widthPixels;

        initPath();
    }

    public BgView(Context context) {
        super(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //this.width = widthMeasureSpec;
        //this.height = heightMeasureSpec;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidthAndState();
        height = getMeasuredHeightAndState();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
        //canvas.drawCircle(getX(),getY(),500,paint);

    }

    private void initPath(){
        // 初始化 路径对象
        path = new Path();
        path.moveTo(0, 198*2 + 64);
        path.quadTo(widthCount/2.0f,198*3,widthCount,198*2 + 64);
        //path.rCubicTo(0, getY()+height/2.0f,widthCount/2.0f,getY()+height,widthCount,getY()+height/2.0f);
        path.lineTo(widthCount,0);
        path.lineTo(0,0);
        path.close();
        Log.d("BgView","\nwidth = "+width+" \nheight = "
                + height +"\ngetX = "+getX() + "\ngetY = " + getY()
                + "\n countWidth = " + widthCount
        );
    }


}
