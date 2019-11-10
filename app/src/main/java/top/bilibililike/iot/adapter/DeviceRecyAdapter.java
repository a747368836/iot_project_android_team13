package top.bilibililike.iot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.bilibililike.iot.R;
import top.bilibililike.iot.bean.ClimateBean;
import top.bilibililike.iot.bean.DeviceBean;
import top.bilibililike.iot.utils.ControlCallback;
import top.bilibililike.iot.utils.animation.FlipCardAnimation;

public class DeviceRecyAdapter extends RecyclerView.Adapter<DeviceRecyAdapter.ViewHolder> {


    private Context mContext;
    private List<DeviceBean.DataBean> deviceList;
    private Unbinder unbinder;
    private ClimateBean.DataBean climateBean;
    private int[] climatePosition;

    private ControlCallback controler;

    public DeviceRecyAdapter(Context context, List<DeviceBean.DataBean> deviceList, ControlCallback callback) {
        this.mContext = context;
        this.deviceList = deviceList;
        this.controler = callback;
        int climateNum = 0;
        for (int i = 0; i < deviceList.size(); i++) {
            if (deviceList.get(i).getType().equals("climate")) {
                climateNum++;
            }
        }
        climatePosition = new int[climateNum];
        climateNum = 0;
        for (int i = 0; i < deviceList.size(); i++) {
            if (deviceList.get(i).getType().equals("climate")) {
                climatePosition[climateNum] = i;
                climateNum++;
            }
        }

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
        DeviceBean.DataBean dataBean = deviceList.get(position);
        if (dataBean.getType().equals("light")) {
            Glide.with(mContext)
                    .load(mContext.getDrawable(R.mipmap.ic_light))
                    .into(holder.imgIcon);
            holder.itemView.setOnClickListener(v -> {
                controler.ledChange(dataBean.getType());
            });
            holder.itemView.setOnLongClickListener(v -> {
                controler.ledTwinkle(dataBean.getType());
                return false;
            });
        } else if (dataBean.getType().equals("current")) {
            Glide.with(mContext)
                    .load(mContext.getDrawable(R.mipmap.ic_current))
                    .into(holder.imgIcon);


        } else if (dataBean.getType().equals("fan0")) {
            Glide.with(mContext)
                    .load(mContext.getDrawable(R.mipmap.ic_fan))
                    .into(holder.imgIcon);


        } else if (dataBean.getType().equals("fan1")) {
            Glide.with(mContext)
                    .load(mContext.getDrawable(R.mipmap.ic_fan))
                    .into(holder.imgIcon);


        } else if (dataBean.getType().equals("climate")) {
            holder.itemView.setOnClickListener(v -> holder.itemView.startAnimation(
                    getAnimation(holder.itemView, holder.containerLinear, holder.containerLinearBack)));
            if (climateBean != null) {
                holder.backTvContent.setText("当前温度:" + climateBean.getTemperature() + "°C\n"
                        + "当前湿度:" + climateBean.getHumidity() + "%\n"
                        + "烟雾浓度：" + climateBean.getSmoke() + "%"
                );
            } else {
                holder.backTvContent.setText("暂未采集到温湿度信息");
            }
            Glide.with(mContext)
                    .load(mContext.getDrawable(R.mipmap.ic_climate))
                    .into(holder.imgIcon);
        } else if (dataBean.getType().equals("alarm")) {
            Glide.with(mContext)
                    .load(mContext.getDrawable(R.mipmap.ic_alarm))
                    .into(holder.imgIcon);
        }
        holder.tvName.setText(dataBean.getObject());


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
        @BindView(R.id.container_linear_back)
        LinearLayout containerLinearBack;
        @BindView(R.id.img_icon)
        ImageView imgIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.back_tv_content)
        TextView backTvContent;

        FlipCardAnimation animation;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);


        }
    }


    public void unbind() {
        unbinder.unbind();
    }

    public Unbinder getBinder() {
        return unbinder;
    }

    public void refreshData(DeviceBean deviceBean) {
        if (deviceBean.getData() != null) {
            this.deviceList = deviceBean.getData();
        }
        notifyDataSetChanged();
    }

    public void refreshData(ClimateBean.DataBean climateBean) {
        this.climateBean = climateBean;
        for (int i = 0; i < climatePosition.length; i++) {
            notifyItemChanged(climatePosition[i]);
        }
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
