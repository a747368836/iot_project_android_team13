package top.bilibililike.iot.widget;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.bilibililike.iot.R;
import top.bilibililike.iot.bean.ReportHistoryBean;
import top.bilibililike.iot.bean.ReportHistoryBean.DataBean;

public class HistoryAdapter extends BaseQuickAdapter<ReportHistoryBean.DataBean, BaseViewHolder> {

    public HistoryAdapter(int layoutResId,
            @Nullable List<DataBean> data) {
        super(layoutResId, data);
    }

    public HistoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DataBean dataBean) {
        long time = Long.parseLong(dataBean.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date(time);
        String t1 = format.format(d1);
        baseViewHolder.setText(R.id.text_name,dataBean.getUsername())
                .setText(R.id.text_location,dataBean.getAddress())
                .setText(R.id.text_time,t1);
    }
}
