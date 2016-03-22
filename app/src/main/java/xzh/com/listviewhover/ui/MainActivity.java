package xzh.com.listviewhover.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import xzh.com.listviewhover.R;
import xzh.com.listviewhover.adapter.AdverViewAdapter;
import xzh.com.listviewhover.model.AdverNotice;
import xzh.com.listviewhover.model.LikeEntity;
import xzh.com.listviewhover.utils.DateUtil;
import xzh.com.listviewhover.view.AdverView;
import xzh.com.listviewhover.view.StarView;
import xzh.com.listviewhover.view.picker.DatePicker;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.list_button)
    Button listButton;
    @InjectView(R.id.starview)
    StarView starview;
    @InjectView(R.id.date_picker)
    TextView datePicker;
    @InjectView(R.id.like_count)
    ImageView likeCount;
    @InjectView(R.id.adver)
    AdverView adver;


    private List<AdverNotice> datas = new ArrayList<AdverNotice>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();
        initAdver();
    }

    private void initAdver() {
        datas.add(new AdverNotice("瑞士维氏军刀 新品满200-50","最新"));
        datas.add(new AdverNotice("家居家装焕新季，讲199减100！","最火爆"));
        datas.add(new AdverNotice("带上相机去春游，尼康低至477","HOT"));
        datas.add(new AdverNotice("价格惊呆！电信千兆光纤上市","new"));
        AdverViewAdapter adapter = new AdverViewAdapter(datas);
        adver.setAdapter(adapter);
        adver.start();
    }

    @OnClick(R.id.list_button)
    void click(View view) {
        startActivity(new Intent(this, SelectedCityActivity.class));
    }

    @OnClick(R.id.pop_button)
    void popClick(View view) {
        startActivity(new Intent(this, PopWindowActivity.class));
    }

    @OnClick(R.id.date_picker)
    void dateClick(View view) {
        chooseDate();
    }

    @OnClick(R.id.like_count)
    void likeClick(View view) {
        like();
    }

    private void like() {
        LikeEntity entity=new LikeEntity();
        entity.liked=false;
        entity.count=99;
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        if (entity.liked) {
            entity.liked=false;
            entity.count=entity.count - 1;
            likeCount.setImageResource(R.drawable.ic_thumb_up_gray_18dp);
        } else {
            entity.liked=true;
            entity.count=entity.count + 1;
            likeCount.setImageResource(R.drawable.ic_thumb_up_red_18dp);
        }
        likeCount.startAnimation(animation);
    }

    private void chooseDate() {
        DatePicker picker = new DatePicker(this);
        picker.setRange(2000, 2020);
        picker.setSelectedItem(DateUtil.getYear(), DateUtil.getMonth(), DateUtil.getDay());
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                datePicker.setText(year + "-" + month + "-" + day);
            }
        });
        picker.show();
    }


    private void init() {
        starview.setScore(3.5f);
        datePicker.setText(DateUtil.getToday());
    }
}


