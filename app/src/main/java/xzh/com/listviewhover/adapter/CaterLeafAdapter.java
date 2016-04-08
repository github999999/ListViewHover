package xzh.com.listviewhover.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import xzh.com.listviewhover.R;
import xzh.com.listviewhover.model.CatergoryModel;
import xzh.com.listviewhover.model.CatergoryModel.DataEntity.LeafEntity;

/**
 * Created by xiangzhihong on 2016/3/23 on 16:16.
 */
public class CaterLeafAdapter extends BaseAdapter {

    private Context mContext;
    private List<LeafEntity> list;

    public CaterLeafAdapter(Context context) {
        this.mContext = context;
    }

    public CaterLeafAdapter(Context context, List<LeafEntity> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.cater_leaf_item, null);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        LeafEntity entity = list.get(position);
//        viewHolder.itemLeafName.setText(entity.leaf_name);
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.item_leaf_name)
        TextView itemLeafName;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
            view.setTag(this);
        }
    }
}
