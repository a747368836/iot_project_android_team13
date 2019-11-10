package top.bilibililike.iot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.bilibililike.iot.R;
import top.bilibililike.iot.bean.ClimateBean;

public class InfoRecyAdapter extends RecyclerView.Adapter<InfoRecyAdapter.ViewHolder> {

    private Context mContext;
    private ViewHolder holder;
    private ClimateBean.DataBean climateBean;
    private Unbinder unbinder;


    public InfoRecyAdapter(Context context, ClimateBean.DataBean climateBean) {
        this.mContext = context;
        this.climateBean = climateBean;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_info_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (climateBean == null) return;
        if (this.holder == null) {
            this.holder = holder;
        }
        if (position == 0) {
            holder.tvData.setText(climateBean.getTemperature() + "°C");
            holder.tvName.setText("温度");
            holder.imgIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_temperature));
        }else if (position == 1){
            holder.tvData.setText(climateBean.getHumidity() + "%");
            holder.tvName.setText("湿度");
            holder.imgIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_humidity));
        }else if (position == 2){
            holder.tvData.setText(climateBean.getSmoke() + "%");
            holder.tvName.setText("烟雾浓度");
            holder.imgIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_smoke));
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_icon)
        ImageView imgIcon;
        @BindView(R.id.tv_data)
        TextView tvData;
        @BindView(R.id.tv_name)
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setAlpha(0.85f);

        }
    }

    public void unbind() {
        if (unbinder != null){
            unbinder.unbind();
        }
    }

    public void refreshData(ClimateBean.DataBean dataBean){
        this.climateBean = dataBean;
        notifyDataSetChanged();
    }
}
