package xzh.com.listviewhover.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.listviewhover.R;
import xzh.com.listviewhover.model.CatergoryModel;
import xzh.com.listviewhover.model.CatergoryModel.DataEntity;
import xzh.com.listviewhover.model.CatergoryModel.DataEntity.*;
import xzh.com.listviewhover.ui.CatergoryActivity;

/**
 * Created by xiangzhihong on 2015/12/24 on 11:23.
 */
public class CatergoryAdapter extends BaseExpandableListAdapter {

    private CatergoryActivity context;
    //group名称
    private List<DataEntity> groupList;
    //章节的节的列表
    private List<List<MediaEntity>> childList;

    public CatergoryAdapter(CatergoryActivity context, List<DataEntity> groupList, List<List<MediaEntity>> childList) {
        this.context = context;
        this.groupList=groupList;
        this.childList=childList;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHoldView groupHoldView = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.catergory_group_item, null);
            groupHoldView = new GroupHoldView(convertView);
        } else {
            groupHoldView = (GroupHoldView) convertView.getTag();
        }

        initGroup(isExpanded,groupPosition, groupHoldView);
        return convertView;
    }

    private void initGroup(boolean isExpanded, int groupPosition, GroupHoldView groupHoldView) {
       if (isExpanded){
           groupHoldView.groupArrow.setImageResource(R.drawable.expanded_arrow_up);
           groupHoldView.groupLine.setVisibility(View.VISIBLE);
           context.bindLeafData(true);
       }else {
           groupHoldView.groupArrow.setImageResource(R.drawable.expanded_arrow_down);
           groupHoldView.groupLine.setVisibility(View.GONE);
           context.bindLeafData(false);
       }

        DataEntity item = (DataEntity) getGroup(groupPosition);
        String chapterName=item.chapter.name;
        if (!TextUtils.isEmpty(chapterName)) {
            groupHoldView.groupTitle.setText(chapterName);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHold childHoldView = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.catergory_item, null);
            childHoldView = new ChildViewHold(convertView);
        } else {
            childHoldView = (ChildViewHold) convertView.getTag();
        }

        MediaEntity mediaEntity= (MediaEntity) getChild(groupPosition,childPosition);
        childHoldView.itemName.setText(mediaEntity.name);
        initImteClick(childHoldView);
        return convertView;
    }

    private void initImteClick(ChildViewHold childHoldView) {
        childHoldView.childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               context.bindLeafData(true);
            }
        });
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class GroupHoldView {
        @InjectView(R.id.group_title)
        TextView groupTitle;
        @InjectView(R.id.group_arrow)
        ImageView groupArrow;
        @InjectView(R.id.group_line)
        View groupLine;

        GroupHoldView(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }

        public void reset() {
            groupTitle.setText("");
        }
    }


    class ChildViewHold {
        @InjectView(R.id.child_view)
        View childView;
        @InjectView(R.id.item_name)
        TextView itemName;

        ChildViewHold(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }

        public void reset() {
            itemName.setText("");
        }
    }



}
