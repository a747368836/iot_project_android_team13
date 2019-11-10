package top.bilibililike.iot.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import top.bilibililike.iot.R;


/**
 * Created By jay68 on 2018/5/2.
 */
public class UserFragmentItem extends JCardViewPlus {
    private TextView title;
    private ImageView icon;

    public UserFragmentItem(Context context) {
        this(context, null);
    }

    public UserFragmentItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserFragmentItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.mine_layout_user_fragment_item, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserFragmentItem);
        icon = findViewById(R.id.mine_user_item_icon);
        icon.setImageResource(typedArray.getResourceId(R.styleable.UserFragmentItem_itemIcon, 0));
        title = findViewById(R.id.mine_user_item_title);
        title.setText(typedArray.getString(R.styleable.UserFragmentItem_itemTitle));


        setShadowColor(Color.parseColor("#0d000000"));
        typedArray.recycle();
    }


    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setIcon(Drawable icon) {
        this.icon.setImageDrawable(icon);
    }
}
