package top.bilibililike.iot.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.trello.rxlifecycle3.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T> extends RxFragment {
    private Unbinder bind;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bind = ButterKnife.bind(this,view);
        finishCreateView(savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(getLayoutID(),container,false);
    }

    public abstract void finishCreateView(Bundle state);

    protected abstract int getLayoutID();

    @Override
    public void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }

    public abstract void notifyDataSetChanged(T... t);


}
