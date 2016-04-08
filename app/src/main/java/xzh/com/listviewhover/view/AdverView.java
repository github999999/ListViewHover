package xzh.com.listviewhover.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import xzh.com.listviewhover.R;
import xzh.com.listviewhover.adapter.AdverViewAdapter;

public class AdverView extends LinearLayout {
    //控件高度
    private float mAdverHeight = 0f;
    //间隔时间
    private final int mGap = 4000;
    //动画间隔时间
    private final int mAnimDuration = 1000;
    //显示文字的尺寸
    private final float TEXTSIZE = 20f;
    private AdverViewAdapter mAdapter;
    private final float AdverHeight = 50;
    //显示的view
    private View mFirstView;
    private View mSecondView;
    //播放的下标
    private int mPosition;
    //线程的标识
    private boolean isStarted;
    //画笔
    private Paint mPaint;

    public AdverView(Context context) {
        this(context, null);
    }

    public AdverView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setOrientation(VERTICAL);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.adver_view);
        mAdverHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, AdverHeight, getResources().getDisplayMetrics());
        int gap = array.getInteger(R.styleable.adver_view_gap, mGap);
        int animDuration = array.getInteger(R.styleable.adver_view_animDuration, mAnimDuration);

        if (mGap <= mAnimDuration) {
            gap = mGap;
            animDuration = mAnimDuration;
        }
        array.recycle();
    }

    public void setAdapter(AdverViewAdapter adapter) {
        this.mAdapter = adapter;
        setupAdapter();
    }

    public void start() {
        if (!isStarted && mAdapter.getCount() > 1) {
            isStarted = true;
            postDelayed(mRunnable, mGap);
        }
    }

    public void stop() {
        removeCallbacks(mRunnable);
        isStarted = false;
    }

    private void setupAdapter() {
        removeAllViews();
        if (mAdapter.getCount() == 1) {
            mFirstView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            addView(mFirstView);
        } else {
            mFirstView = mAdapter.getView(this);
            mSecondView = mAdapter.getView(this);
            mAdapter.setItem(mFirstView, mAdapter.getItem(0));
            mAdapter.setItem(mSecondView, mAdapter.getItem(1));
            addView(mFirstView);
            addView(mSecondView);
            mPosition = 1;
            isStarted = false;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (LayoutParams.WRAP_CONTENT == getLayoutParams().height) {
            getLayoutParams().height = (int) mAdverHeight;
        } else {
            mAdverHeight = getHeight();
        }

        if (mFirstView != null) {
            mFirstView.getLayoutParams().height = (int) mAdverHeight;
        }
        if (mSecondView != null) {
            mSecondView.getLayoutParams().height = (int) mAdverHeight;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, TEXTSIZE, getResources().getDisplayMetrics()));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText("瑞士维氏军刀", TEXTSIZE, getHeight() * 2 / 3, mPaint);//写文字2/3的高度

    }

    private void performSwitch() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mFirstView, "translationY", mFirstView.getTranslationY() - mAdverHeight);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mSecondView, "translationY", mSecondView.getTranslationY() - mAdverHeight);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1, animator2);//2个动画一起
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFirstView.setTranslationY(0);
                mSecondView.setTranslationY(0);
                View removedView = getChildAt(0);
                mPosition++;
                mAdapter.setItem(removedView, mAdapter.getItem(mPosition % mAdapter.getCount()));
                removeView(removedView);
                addView(removedView, 1);
            }

        });
        set.setDuration(mAnimDuration);
        set.start();
    }

    private AnimRunnable mRunnable = new AnimRunnable();

    private class AnimRunnable implements Runnable {

        @Override
        public void run() {
            performSwitch();
            postDelayed(this, mGap);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
