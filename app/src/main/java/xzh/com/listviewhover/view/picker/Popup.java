package xzh.com.listviewhover.view.picker;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.CallSuper;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import xzh.com.listviewhover.R;


public class Popup {
    private android.app.Dialog dialog;
    private FrameLayout contentLayout;

    public Popup(Context context) {
        init(context);
    }

    private void init(Context context) {
        contentLayout = new FrameLayout(context);
        contentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contentLayout.setFocusable(true);
        contentLayout.setFocusableInTouchMode(true);
        dialog = new android.app.Dialog(context);
        dialog.setCanceledOnTouchOutside(true);//触摸屏幕取消窗体
        dialog.setCancelable(true);//按返回键取消窗体
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);//位于屏幕底部
        window.setWindowAnimations(R.style.Animation_Popup);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setContentView(contentLayout);
    }

    public Context getContext() {
        return contentLayout.getContext();
    }

    public void setAnimationStyle(@StyleRes int animRes) {
        Window window = dialog.getWindow();
        window.setWindowAnimations(animRes);
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    @CallSuper
    public void show() {
        dialog.show();
    }

    @CallSuper
    public void dismiss() {
        dialog.dismiss();
    }

    public void setContentView(View view) {
        contentLayout.removeAllViews();
        contentLayout.addView(view);
    }

    public View getContentView() {
        return contentLayout.getChildAt(0);
    }

    public void setSize(int width, int height) {
        ViewGroup.LayoutParams params = contentLayout.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(width, height);
        } else {
            params.width = width;
            params.height = height;
        }
        contentLayout.setLayoutParams(params);
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        dialog.setOnDismissListener(onDismissListener);
    }

    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        dialog.setOnKeyListener(onKeyListener);
    }

    public Window getWindow() {
        return dialog.getWindow();
    }

    public ViewGroup getRootView() {
        return contentLayout;
    }

}
