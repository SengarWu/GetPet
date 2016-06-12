package com.example.administrator.getpet.ui.HelpStation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.getpet.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/10.
 */
public class AdoptAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdoptAdapter(Context context,List<Map<String,Object>>data)
    {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class ViewHolder
    {
        public ImageView iv_pet_photo;
        public TextView tv_pet_name;
        public TextView tv_username;
        public TextView tv_state;
        public TextView tv_reason;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view = null;
        if (convertView == null)
        {
            view = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.adopt_item,null);
            view.iv_pet_photo = (ImageView) convertView.findViewById(R.id.iv_pet_photo);
            view.tv_pet_name = (TextView) convertView.findViewById(R.id.tv_pet_name);
            view.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            view.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            view.tv_reason = (TextView) convertView.findViewById(R.id.tv_reason);
            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }
        view.iv_pet_photo.setImageResource((Integer) data.get(position).get("pet_photo"));
        view.tv_pet_name.setText((String) data.get(position).get("pet_name"));
        view.tv_username.setText((String) data.get(position).get("username"));
        view.tv_state.setText((String) data.get(position).get("state"));
        view.tv_reason.setText((String) data.get(position).get("reason"));
        return convertView;
    }
}
