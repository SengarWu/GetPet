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
 * Created by Administrator on 2016/6/5.
 */
public class MyPetAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    //设置删除按钮接口
    private View.OnClickListener onDelete;

    //设置接口方法
    public void setOnDelete(View.OnClickListener onDelete)
    {
        this.onDelete = onDelete;
    }

    public MyPetAdapter(Context context,List<Map<String,Object>>data)
    {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class ViewHolder
    {
        public ImageView iv_pet_photo;
        public TextView tv_pet_name;
        public TextView tv_age;
        public TextView tv_category;
        public TextView tv_character;
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
            convertView = layoutInflater.inflate(R.layout.my_pet_item,null);
            view.iv_pet_photo = (ImageView) convertView.findViewById(R.id.iv_pet_photo);
            view.tv_pet_name = (TextView) convertView.findViewById(R.id.tv_pet_name);
            view.tv_age = (TextView) convertView.findViewById(R.id.tv_age);
            view.tv_category = (TextView) convertView.findViewById(R.id.tv_category);
            view.tv_character = (TextView) convertView.findViewById(R.id.tv_character);
            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        view.iv_pet_photo.setImageResource((Integer) data.get(position).get("pet_photo"));
        view.tv_pet_name.setText((String) data.get(position).get("pet_name"));
        view.tv_age.setText((String) data.get(position).get("pet_age"));
        view.tv_category.setText((String)data.get(position).get("pet_category"));
        view.tv_character.setText((String)data.get(position).get("pet_character"));
        return convertView;
    }
}
