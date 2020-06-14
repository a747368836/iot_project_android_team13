package top.bilibililike.iot.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class ReportInputView extends LinearLayout {

    public static final int TYPE_INPUT_TEXT = 0x01;
    public static final int TYPE_SELECT = 0x02;

    private TextView leftTextView;
    private EditText inputView;
    private TextView rightTextView;
    private ImageView rightArrowView;


    private ReportInputView(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public ReportInputView(Context context,int type){
        this(context);
        if (type == TYPE_INPUT_TEXT){
            leftTextView = new TextView(context);
            inputView = new EditText(context);
        }else if (type == TYPE_SELECT){
            leftTextView = new TextView(context);
            rightArrowView = new ImageView(context);
            inputView = new EditText(context);
        }
        leftTextView.setTextSize(14, TypedValue.COMPLEX_UNIT_SP);
        leftTextView.setGravity(Gravity.CENTER);
    }

    public ReportInputView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }




}
