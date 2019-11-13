package top.bilibililike.iot.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.bilibililike.iot.R;
import top.bilibililike.iot.bean.ClimateBean;
import top.bilibililike.iot.bean.DeviceBean;
import top.bilibililike.iot.utils.ControlCallback;
import top.bilibililike.iot.utils.OnDoubleClickListener;
import top.bilibililike.iot.utils.ToastUtil;
import top.bilibililike.iot.utils.animation.FlipCardAnimation;

public class DeviceRecyAdapter extends RecyclerView.Adapter<DeviceRecyAdapter.ViewHolder> {


    private Context mContext;
    private List<DeviceBean.DataBean> deviceList;
    private Unbinder unbinder;
    private ViewHolder holder;

    private ControlCallback controler;

    public DeviceRecyAdapter(Context context, List<DeviceBean.DataBean> deviceList, ControlCallback callback) {
        this.mContext = context;
        this.deviceList = deviceList;
        this.controler = callback;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_device_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (this.holder == null){
            this.holder = holder;
        }
        DeviceBean.DataBean dataBean = deviceList.get(position);
        if (dataBean.getType().equals("light")) {
            holder.tvName.setText("关");
            holder.tvType.setText(dataBean.getObject());
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
            holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_light_off));
            holder.itemView.setOnClickListener(v -> {
                    controler.ledChange(dataBean.getType());
                    holder.tvName.setText("开启");
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_light_on));
                    holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_selected));
            });
            holder.itemView.setOnLongClickListener(v -> {
                if (holder.tvName.getText().toString().equals("闪烁")){
                    controler.ledChange(dataBean.getType());
                    holder.tvName.setText("关闭");
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_light_off));
                    holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
                }else {
                    controler.ledTwinkle(dataBean.getType());
                    holder.tvName.setText("闪烁");
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_light_on));
                    holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_selected));
                }

                return true;
            });
        } else if (dataBean.getType().equals("current")) {
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
            holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_curtain_close));
            holder.tvName.setText("关");
            holder.tvType.setText(dataBean.getObject());
            holder.itemView.setOnClickListener(v -> {
                holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_selected));
                if (holder.tvName.getText().toString().equals("关")){
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_curtain_on_half));
                    holder.tvName.setText("半开");
                    controler.currentControl("half");
                }else if (holder.tvName.getText().toString().equals("半开")){
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_curtain_on_full));
                    holder.tvName.setText("全开");
                    controler.currentControl("full");
                }else if (holder.tvName.getText().toString().equals("全开")){
                    holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_curtain_close));
                    holder.tvName.setText("关");
                    controler.currentControl("close");
                }
            });

            holder.itemView.setOnLongClickListener( v -> {
                holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
                holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_curtain_close));
                holder.tvName.setText("关");
                controler.currentControl("close");
                return true;
            });



        } else if (dataBean.getType().equals("fan0")) {
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
            holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_fan_off));
            holder.tvName.setText("关");
            holder.tvType.setText(dataBean.getObject());
            holder.itemView.setOnClickListener(v->{
                holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_selected));
                holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_fan_on));
                if (holder.tvName.getText().toString().equals("关")){
                    holder.tvName.setText("一档");
                    controler.fanControl(0,"1");
                }else if (holder.tvName.getText().toString().equals("一档")){
                    holder.tvName.setText("二档");
                    controler.fanControl(0,"2");
                }else if (holder.tvName.getText().toString().equals("二档")){
                    holder.tvName.setText("三档");
                    controler.fanControl(0,"3");
                }else if (holder.tvName.getText().toString().equals("三档")){
                    holder.tvName.setText("关");
                    controler.fanControl(0,"off");
                    holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_fan_off));
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    holder.tvName.setText("关");
                    controler.fanControl(0,"off");
                    holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_fan_off));
                    return true;
                }
            });
        } else if (dataBean.getType().equals("fan1")) {
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
            holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_fan_off));
            holder.tvName.setText("关");
            holder.tvType.setText(dataBean.getObject());
            holder.itemView.setOnClickListener(v->{
                holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_selected));
                holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_fan_on));
                if (holder.tvName.getText().toString().equals("关")){
                    holder.tvName.setText("一档");
                    controler.fanControl(1,"1");
                }else if (holder.tvName.getText().toString().equals("一档")){
                    holder.tvName.setText("二档");
                    controler.fanControl(1,"2");
                }else if (holder.tvName.getText().toString().equals("二档")){
                    holder.tvName.setText("三档");
                    controler.fanControl(1,"3");
                }else if (holder.tvName.getText().toString().equals("三档")){
                    holder.tvName.setText("关");
                    controler.fanControl(1,"off");
                    holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_fan_off));
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    holder.tvName.setText("关");
                    controler.fanControl(1,"off");
                    holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_fan_off));
                    return true;
                }
            });
        }else if (dataBean.getType().equals("alarm")) {
            holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
            holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_alarm_off));
            holder.tvName.setText("关");
            holder.tvType.setText(dataBean.getObject());

            holder.itemView.setOnClickListener( v-> {
                holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_selected));
                holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_alarm_on));
                if (holder.tvName.getText().toString().equals("关")){
                    holder.tvName.setText("报警中");
                    controler.alarmControl("on");
                }else{
                    holder.tvName.setText("关");
                    controler.alarmControl("off");
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_alarm_off));
                    holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
                }
            });

            holder.itemView.setOnLongClickListener( v-> {
                holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_alarm_on));
                holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_selected));
                if (holder.tvName.getText().toString().equals("关")){
                    controler.alarmControl("interval");
                    holder.tvName.setText("间隔报警");

                }else{
                    holder.tvName.setText("关");
                    controler.alarmControl("off");
                    holder.imgIcon.setImageDrawable(mContext.getDrawable(R.mipmap.ic_alarm_off));
                    holder.itemView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_unselected));
                }
                return true;
            });

        }


    }

    @Override
    public int getItemCount() {
        if (deviceList == null || deviceList.size() == 0) {
            return 0;
        } else {
            return deviceList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.container_linear)
        LinearLayout containerLinear;

        @BindView(R.id.img_icon)
        ImageView imgIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;


        FlipCardAnimation animation;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setAlpha(0.95f);
        }
    }


    public void unbind() {
        if (unbinder != null){
            unbinder.unbind();
        }
    }


    public void refreshData(DeviceBean deviceBean) {
        if (deviceBean.getData() != null) {
            this.deviceList = deviceBean.getData();
        }
        notifyDataSetChanged();
    }



    public FlipCardAnimation getAnimation(View itemView, View container, View backView) {
        FlipCardAnimation animation = new FlipCardAnimation(0, 180,
                itemView.getWidth() / 2, itemView.getHeight() / 2);
        animation.setInterpolator(new AnticipateOvershootInterpolator());
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setRepeatCount(0);//设置无限循环
        animation.setOnContentChangeListener(new FlipCardAnimation.OnContentChangeListener() {
            @Override
            public void contentChange() {
                if (container.getVisibility() == View.VISIBLE) {
                    container.setVisibility(View.INVISIBLE);
                    backView.setVisibility(View.VISIBLE);
                } else {
                    container.setVisibility(View.VISIBLE);
                    backView.setVisibility(View.INVISIBLE);
                }

            }
        });
        return animation;
    }


}
