package com.example.administrator.getpet.ui.Home.SendAdopt.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.applyApplication;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;

import java.util.List;

/**
 * Created by Koreleone on 2016-05-23.
 */
public class ApplicationAdapter extends BaseListAdapter<applyApplication> {
    public ApplicationAdapter(Context context, List<applyApplication> items) {
        super(context, items);
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.include_applicationitem, null);
        }
        final applyApplication contract=getList().get(position);
        ImageView sexIcon=(ImageView)ViewHolder.get(convertView,R.id.usersex);
        TextView reputation=(TextView) ViewHolder.get(convertView,R.id.reputation);
        TextView details=(TextView) ViewHolder.get(convertView,R.id.details);
        TextView Subtime=(TextView) ViewHolder.get(convertView,R.id.Subtime);
        TextView connnect_phone=(TextView) ViewHolder.get(convertView,R.id.connect_phone);
        TextView connnect_place=(TextView) ViewHolder.get(convertView,R.id.connect_place);
        final TextView state=(TextView)ViewHolder.get(convertView,R.id.state);
        Button select=(Button)ViewHolder.get(convertView,R.id.select);
        //显示性别，用户名
        if(contract.getUsers().getSex()=="男"){
            sexIcon.setImageResource(R.mipmap.boy);
        }else if(contract.getUsers().getSex()=="女"){
            sexIcon.setImageResource(R.mipmap.girl);
        }else {
            sexIcon.setImageResource(R.mipmap.notknow);
        }
        reputation.setText(String.valueOf(contract.getUsers().getUser_reputation()));
        details.setText(contract.getDetail());
        Subtime.setText(TimeUtils.dateToString(contract.getApplyDate(),TimeUtils.FORMAT_DATE_TIME_SECOND));
        if(contract.getResult()==0){
            state.setText("未同意");
        }else if(contract.getResult()==1){
            state.setText("已同意");
        }else{
            state.setText("已失效");
        }
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contract.getResult()==0){
                    SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("applyApplication","updateColumnsByWheres");
                    httpReponse.addWhereParams("id","=",contract.getId());
                    httpReponse.addColumnParams("result","1");
                    httpReponse.updateColumnsByWheres( new HttpCallBack() {
                        @Override
                        public void Success(String data) {
                            Toast.makeText(mContext,"已同意", Toast.LENGTH_LONG).show();
                            state.setText("已同意");
                            contract.setResult(1);
                        }
                        @Override
                        public void Fail(String e) {
                            Toast.makeText(mContext,"操作失败", Toast.LENGTH_LONG).show();
                        }
                    });
                }else if(contract.getResult()==1){
                    Toast.makeText(mContext,"已同意", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mContext, "已失效", Toast.LENGTH_LONG).show();
                }
            }
        });
        return convertView;
    }

}
