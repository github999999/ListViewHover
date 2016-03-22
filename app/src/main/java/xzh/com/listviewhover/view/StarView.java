package xzh.com.listviewhover.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.listviewhover.R;


public class StarView extends LinearLayout {

    @InjectView(R.id.star_lable)
    TextView starLable;
    @InjectView(R.id.star_img1)
    ImageView starImg1;
    @InjectView(R.id.star_img2)
    ImageView starImg2;
    @InjectView(R.id.star_img3)
    ImageView starImg3;
    @InjectView(R.id.star_img4)
    ImageView starImg4;
    @InjectView(R.id.star_img5)
    ImageView starImg5;

    private Context mContext;
    private LayoutInflater inflater;
    private LinearLayout rootView;
    private float mScore;
    private boolean isBig = false;
    static final int STAR_ON = 1;
    static final int STAR_OFF = 2;
    static final int STAR_HALF = 3;

    public StarView(Context context) {
        super(context);
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        initUi();
    }

    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        initUi();
    }


    private void initUi() {
        rootView = (LinearLayout) inflater.inflate(R.layout.view_star, null);
        ButterKnife.inject(this,rootView);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(rootView, lp);
        starLable.setVisibility(View.GONE);

        setStarState(starImg1, STAR_OFF);
        setStarState(starImg2, STAR_OFF);
        setStarState(starImg3, STAR_OFF);
        setStarState(starImg4, STAR_OFF);
        setStarState(starImg5, STAR_OFF);
    }

    public void setStarLable(String lable) {
        if (null != lable) {
            starLable.setVisibility(View.VISIBLE);
            starLable.setText(lable + "：");
        } else {
            starLable.setVisibility(View.GONE);
        }
    }


    /**
     * 设置星显示个数
     *
     * @param score float 分数
     */
    public void setScore(float score) {
        mScore = score;
        String str = Float.toString(mScore);
        int index = str.indexOf(".");
        int intPart = Integer.parseInt(str.substring(0, index));
        int floatPart = Integer.parseInt(str.substring(index + 1, index + 2));
        Log.i("------setScore---", index + "---" + intPart + "----" + floatPart);
        switch (intPart) {
            case 0:
                setFloatPart(starImg1, floatPart);
                break;
            case 1:
                setStarState(starImg1, STAR_ON);
                setFloatPart(starImg2, floatPart);
                break;
            case 2:
                setStarState(starImg1, STAR_ON);
                setStarState(starImg2, STAR_ON);
                setFloatPart(starImg3, floatPart);
                break;
            case 3:
                setStarState(starImg1, STAR_ON);
                setStarState(starImg2, STAR_ON);
                setStarState(starImg3, STAR_ON);
                setFloatPart(starImg4, floatPart);
                break;
            case 4:
                setStarState(starImg1, STAR_ON);
                setStarState(starImg2, STAR_ON);
                setStarState(starImg3, STAR_ON);
                setStarState(starImg4, STAR_ON);
                setFloatPart(starImg5, floatPart);
                break;
            case 5:
                setStarState(starImg1, STAR_ON);
                setStarState(starImg2, STAR_ON);
                setStarState(starImg3, STAR_ON);
                setStarState(starImg4, STAR_ON);
                setStarState(starImg5, STAR_ON);
                break;
        }

    }


    /**
     * 设置小数部分星星显示状态
     *
     * @param imgView
     * @param floatScore
     */
    private void setFloatPart(ImageView imgView, int floatScore) {
        if (floatScore > 0 && floatScore < 5) {
            setStarState(imgView, STAR_HALF);
            return;
        }

        if (floatScore >= 5 && floatScore < 10) {
            setStarState(imgView, STAR_ON);
            return;
        }
    }

    /**
     * 设置星星显示状态
     *
     * @param imgView
     * @param state   1 黄色   2 灰色  3一半黄色一半灰色
     */
    private void setStarState(ImageView imgView, int state) {
        imgView.setTag(state);
        if (isBig) {
            setBigStar(imgView, state);
        } else {
            setSmallStar(imgView, state);
        }
    }

    private void setBigStar(ImageView imgView, int state) {
        switch (state) {
            case STAR_ON:
                imgView.setImageResource(R.drawable.review_big_star_on);
                break;
            case STAR_OFF:
                imgView.setImageResource(R.drawable.review_big_star_off);
                break;
            case STAR_HALF:
                imgView.setImageResource(R.drawable.review_big_star_half);
        }
    }

    private void setSmallStar(ImageView imgView, int state) {
        switch (state) {
            case STAR_ON:
                imgView.setImageResource(R.drawable.review_big_star_on);
                break;
            case STAR_OFF:
                imgView.setImageResource(R.drawable.review_big_star_off);
                break;
            case STAR_HALF:
                imgView.setImageResource(R.drawable.review_big_star_half);
        }
    }

    public boolean isBig() {
        return isBig;
    }

    public void setBig(boolean style) {
        if (isBig == style) {
            return;
        }
        this.isBig = style;
        int state = starImg1.getTag() == null ? 2 : Integer.parseInt(starImg1.getTag().toString());
        setStarState(starImg1, state);
        state = starImg2.getTag() == null ? 2 : Integer.parseInt(starImg1.getTag().toString());
        setStarState(starImg2, state);
        state = starImg3.getTag() == null ? 2 : Integer.parseInt(starImg1.getTag().toString());
        setStarState(starImg3, state);
        state = starImg4.getTag() == null ? 2 : Integer.parseInt(starImg1.getTag().toString());
        setStarState(starImg4, state);
        state = starImg5.getTag() == null ? 2 : Integer.parseInt(starImg1.getTag().toString());
        setStarState(starImg5, state);
    }


}
