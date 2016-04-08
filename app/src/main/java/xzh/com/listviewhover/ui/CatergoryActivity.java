package xzh.com.listviewhover.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.listviewhover.R;
import xzh.com.listviewhover.adapter.CaterLeafAdapter;
import xzh.com.listviewhover.adapter.CatergoryAdapter;
import xzh.com.listviewhover.base.OnItemClickLisener;
import xzh.com.listviewhover.model.CatergoryModel;
import xzh.com.listviewhover.model.CatergoryModel.DataEntity;
import xzh.com.listviewhover.model.CatergoryModel.DataEntity.MediaEntity;
import xzh.com.listviewhover.utils.JsonUtil;
import xzh.com.listviewhover.utils.Utils;
import xzh.com.listviewhover.view.MyExpandableListView;
import xzh.com.listviewhover.view.MyListView;

/**
 * Created by xiangzhihong on 2016/3/23 on 10:52.
 * 京东的三级菜单测试
 */
public class CatergoryActivity extends Activity {

    @InjectView(R.id.expan_listview)
    MyExpandableListView expanListview;
    @InjectView(R.id.listview)
    MyListView listview;

    private Context context = null;
    private CatergoryAdapter adapter = null;
    private List<DataEntity> groupList;
    private List<List<MediaEntity>> childList;
    private CaterLeafAdapter leafAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catergory);
        ButterKnife.inject(this);
        context = CatergoryActivity.this;
        init();
    }

    private void init() {
        expanListview
                .setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS);
        getData();
        initAdapter();
    }

    private void initAdapter() {

    }

    private void getData() {
        String content = Utils.getFromAssets(this, "catergory_default.txt");
        if (content != null) {
            CatergoryModel model = JsonUtil.parseJson(content, CatergoryModel.class);
            if (model != null && model.data != null && model.data.size() > 0) {
                bindData(model.data);
            }
        }
    }

    private void bindData(List<DataEntity> data) {
        //考虑到这种数据结构，只能有这种循环了
        groupList = new ArrayList<DataEntity>();
        childList = new ArrayList<List<MediaEntity>>();
        for (int i = 0; i < data.size(); i++) {
            groupList.add(data.get(i));
            List<MediaEntity> child = new ArrayList<MediaEntity>();
            for (int j = 0; j < data.get(i).media.size(); j++) {
                MediaEntity entity = data.get(i).media.get(j);
                child.add(entity);
            }
            childList.add(child);
        }
        adapter = new CatergoryAdapter(CatergoryActivity.this, groupList, childList);
        expanListview.setAdapter(adapter);
        //默认展开
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expanListview.expandGroup(i);
        }
    }

    public void bindLeafData(boolean isVisiable) {
        leafAdapter = new CaterLeafAdapter(context);
        listview.setAdapter(leafAdapter);
        //控制显示
        listview.setVisibility(isVisiable ? View.VISIBLE : View.GONE);
    }


    public static void open(Activity context) {
        context.startActivity(new Intent(context, CatergoryActivity.class));
    }

    private OnItemClickLisener onItemClickLisener = null;

    public void setOnItemListener(OnItemClickLisener onItemClickLisener) {
        this.onItemClickLisener = onItemClickLisener;

    }
}
