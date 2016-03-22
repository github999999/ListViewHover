package xzh.com.listviewhover.view.picker;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.CallSuper;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import xzh.com.listviewhover.utils.ScreenUtils;


/**
 * 底部弹窗基类
 */
public abstract class BottomPopup<V extends View> implements DialogInterface.OnKeyListener {

    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    protected Activity activity;
    protected int screenWidthPixels;
    protected int screenHeightPixels;
    private Popup popup;
    private int width = 0, height = 0;
    private boolean isFillScreen = false;
    private boolean isHalfScreen = false;

    public BottomPopup(Activity activity) {
        this.activity = activity;
        DisplayMetrics displayMetrics = ScreenUtils.displayMetrics(activity);
        screenWidthPixels = displayMetrics.widthPixels;
        screenHeightPixels = displayMetrics.heightPixels;
        popup = new Popup(activity);
        popup.setOnKeyListener(this);
    }

    protected abstract V makeContentView();

    private void onShowPrepare() {
        setContentViewBefore();
        V view = makeContentView();
        popup.setContentView(view);
        setContentViewAfter(view);
        if (width == 0 && height == 0) {
            //未明确指定宽高，优先考虑全屏再考虑半屏然后再考虑包裹内容
            width = screenWidthPixels;
            if (isFillScreen) {
                height = MATCH_PARENT;
            } else if (isHalfScreen) {
                height = screenHeightPixels / 2;
            } else {
                height = WRAP_CONTENT;
            }
        }
        popup.setSize(width, height);
    }

    public void setFillScreen(boolean fillScreen) {
        isFillScreen = fillScreen;
    }

    public void setHalfScreen(boolean halfScreen) {
        isHalfScreen = halfScreen;
    }

    protected void setContentViewBefore() {
    }

    protected void setContentViewAfter(V contentView) {
    }

    public void setAnimationStyle(@StyleRes int animRes) {
        popup.setAnimationStyle(animRes);
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        popup.setOnDismissListener(onDismissListener);
    }

    // fixed: 2016/1/26 修复显示之前设置宽高无效问题
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isShowing() {
        return popup.isShowing();
    }

    @CallSuper
    public void show() {
        onShowPrepare();
        popup.show();
    }

    /**
     * Dismiss.
     */
    public void dismiss() {
        popup.dismiss();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public final boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            return onKeyDown(keyCode, event);
        }
        return false;
    }

    public Window getWindow() {
        return popup.getWindow();
    }

    public ViewGroup getRootView() {
        return popup.getRootView();
    }

}
