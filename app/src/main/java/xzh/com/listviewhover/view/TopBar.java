package xzh.com.listviewhover.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.listviewhover.R;


public class TopBar extends LinearLayout {
    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.title)
    TextView title;

   private   Context context;

    public TopBar(Context context) {
        super(context);
        this.context = context;
        initTopbar();
    }


    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initTopbar();
    }

    public void initTopbar() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_top_bar, this);
        ButterKnife.inject(this, v);
    }

    public ImageView getBarback() {
        back.setVisibility(View.VISIBLE);
        return back;
    }

    public TextView getBarTitle() {
        title.setVisibility(View.VISIBLE);
        return title;
    }

    public void setTitle(int msgid) {
        setTitle(getResources().getString(msgid));
    }

    public void setTitle(String title) {
        if (title != null) {
            getBarTitle().setText(title);
        }
    }

    public void finishActivity(Activity activity){
        if (activity != null&&back!=null) {
            activity.finish();
        }
    }

}
