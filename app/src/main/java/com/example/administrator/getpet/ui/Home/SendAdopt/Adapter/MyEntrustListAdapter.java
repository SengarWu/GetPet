package com.example.administrator.getpet.ui.Home.SendAdopt.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.entrust;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;

import java.util.List;

/**
 * Created by Koreleone on 2016-05-16.
 */
public class MyEntrustListAdapter extends BaseListAdapter<entrust> {
    public MyEntrustListAdapter(Context context, List<entrust> items) {
        super(context, items);
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.myentrustlist_item, null);
        }
        final entrust contract=getList().get(position);
        TextView title=(TextView) ViewHolder.get(convertView,R.id.title);
        TextView pet=(TextView) ViewHolder.get(convertView,R.id.pet);
        TextView award=(TextView) ViewHolder.get(convertView,R.id.award);
        final TextView applynum=(TextView) ViewHolder.get(convertView,R.id.applynum);
        TextView publishTime=(TextView) ViewHolder.get(convertView,R.id.publishTime);
        TextView state=(TextView) ViewHolder.get(convertView,R.id.state);
        //显示该消息的状态
        switch (contract.getStatus()){
            case "正常":
                state.setText("等待申请");
                break;
            default:
                state.setText(contract.getStatus());
                break;
        }
        if(contract.getTitle()!=null) {
            title.setText(contract.getTitle());
        }
        if(contract.getPet()!=null) {
            pet.setText(contract.getPet().name);
        }
        award.setText(String.valueOf(contract.getAward()));
        if(contract.getDate()!=null) {
            publishTime.setText(TimeUtils.dateToString(contract.getDate(),TimeUtils.FORMAT_DATE_TIME_SECOND));
        }

        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","QueryCount");
        //添加条件 查询对于该寄养信息的领养申请
        httpReponse.addWhereParams("entrustId","=",contract.getId());
        //调用QueryCount方法
        httpReponse.QueryCount(new HttpCallBack() {
            @Override
            public void Success(String data) {
               applynum.setText(data);
            }
            @Override
            public void Fail(String e)
            {
                applynum.setText("未知");
            }
        });
        return convertView;
    }
}
