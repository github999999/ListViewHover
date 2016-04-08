package xzh.com.listviewhover.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

import xzh.com.listviewhover.R;

/**
 * Created by xiangzhihong on 2016/4/7 on 15:43.
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
    }

    private void init() {
        HashMap map=new HashMap();
       Thread subThead=new Thread(){
           @Override
           public void run() {
               Handler handler=new Handler();
               Message msg=new Message();
               msg.obj="test";
           }
       };
    }
}
