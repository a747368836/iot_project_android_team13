package top.bilibililike.iot.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.bilibililike.iot.R;
import top.bilibililike.iot.base.BaseActivity;
import top.bilibililike.iot.utils.Status;
import top.bilibililike.iot.view.WaveView;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.edt_account)
    TextInputEditText edtAccount;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.card_input)
    CardView cardInput;

    @BindView(R.id.view_wave)
    WaveView waveView;

    @BindView(R.id.progress_bar)
    LinearLayout progressBar;

    int originML,originMR;



    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initClick();

    }

    private void initClick() {

        imgBack.setOnClickListener(v -> onBackPressed());
        tvLogin.setOnClickListener(v -> {
            int width = tvLogin.getWidth();
            int height = tvLogin.getHeight();
            inputAnimator(tvLogin, width, height);
            String account = edtAccount.getText().toString();
            String password = edtPassword.getText().toString();
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Status.setIsLogin(true);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }).start();
        });
        tvRegister.setOnClickListener( v -> {
            recovery();
        });

    }


    /**
     * 输入框的动画效果
     *
     * @param view 控件
     * @param w    宽
     * @param h    高
     */
    private void inputAnimator(final View view, float w, float h) {
        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) cardInput
                .getLayoutParams();
        originML = param.leftMargin;
        originMR = param.rightMargin;
        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(animation -> {
            float value = (Float) animation.getAnimatedValue();
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                    .getLayoutParams();
            params.leftMargin = (int) value;
            params.rightMargin = (int) value;
            view.setLayoutParams(params);
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(cardInput,
                "scaleX", 1f, 0.5f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(waveView,
                "scaleX", 1f, 0.5f);
        set.setDuration(500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2,animator3);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                waveView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                progressAnimator(progressBar);
                cardInput.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

    }


    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(500);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }

    /**
     * 恢复初始状态
     */
    private void recovery() {
        progressBar.setVisibility(View.GONE);
        cardInput.setVisibility(View.VISIBLE);
        waveView.setVisibility(View.VISIBLE);
        tvLogin.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cardInput.getLayoutParams();
        params.leftMargin = originML;
        params.rightMargin = originMR;
        cardInput.setLayoutParams(params);

        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) tvLogin.getLayoutParams();
        param.leftMargin = originML *2 ;
        param.rightMargin = originMR *2;
        tvLogin.setLayoutParams(param);

        ObjectAnimator animator = ObjectAnimator.ofFloat(tvLogin,"scaleX",0.5f,1f);
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(cardInput, "scaleX", 0.5f,1f );
        animator1.setDuration(500);
        animator1.setInterpolator(new AccelerateDecelerateInterpolator());
        animator1.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(waveView,"scaleX",0.5f,1f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();




    }




    public class JellyInterpolator extends LinearInterpolator {
        private float factor;

        JellyInterpolator() {
            this.factor = 0.15f;
        }

        @Override
        public float getInterpolation(float input) {
            return (float) (Math.pow(2, -10 * input)
                    * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
        }
    }


}
