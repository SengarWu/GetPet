package com.example.administrator.getpet.ui.Me.adapter;

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
 * Created by Administrator on 2016/5/27.
 */
public class sPetAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public sPetAdapter(Context context,List<Map<String,Object>>data)
    {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class ViewHolder
    {
        public ImageView iv_pet_photo;
        public TextView tv_pet_name;
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
            convertView = layoutInflater.inflate(R.layout.spet_item,null);
            view.iv_pet_photo = (ImageView) convertView.findViewById(R.id.iv_pet_photo);
            view.tv_pet_name = (TextView) convertView.findViewById(R.id.tv_pet_name);
            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }
        //绑定数据
       /* if (TextUtils.isEmpty((String)data.get(position).get("pet_photo")))
        {
            view.iv_pet_photo.setImageResource(R.mipmap.pet_default_photo);
        }
        else
        {
            ImageDownLoader.showNetImage(CustomApplication.getmInstance(),
                    (String) data.get(position).get("pet_photo"),view.iv_pet_photo,
                    R.mipmap.pet_default_photo);
        }*/
        view.iv_pet_photo.setImageResource((Integer) data.get(position).get("pet_photo"));
        view.tv_pet_name.setText((String) data.get(position).get("pet_name"));
        return convertView;
    }
}
