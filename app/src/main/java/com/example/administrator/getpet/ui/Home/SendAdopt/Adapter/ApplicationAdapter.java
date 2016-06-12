package com.example.administrator.getpet.ui.Home.SendAdopt.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private Context Curcontext;
    public ApplicationAdapter(Context context, List<applyApplication> items) {
        super(context, items);
        Curcontext=context;
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.include_applicationitem, null);
        }
        final applyApplication contract=getList().get(position);
        ImageView sexIcon=(ImageView)ViewHolder.get(convertView,R.id.usersex);
        TextView username=(TextView) ViewHolder.get(convertView,R.id.username);
        TextView reputation=(TextView) ViewHolder.get(convertView,R.id.reputation);
        TextView details=(TextView) ViewHolder.get(convertView,R.id.details);
        TextView Subtime=(TextView) ViewHolder.get(convertView,R.id.Subtime);
        TextView connnect_phone=(TextView) ViewHolder.get(convertView,R.id.connect_phone);
        TextView connnect_place=(TextView) ViewHolder.get(convertView,R.id.connect_place);
        final TextView state=(TextView)ViewHolder.get(convertView,R.id.state);
        Button select=(Button)ViewHolder.get(convertView,R.id.select);
        //显示性别，用户名
        username.setText(contract.getUsers().getNickName());
        if(contract.getUsers().getSex()=="男"){
            sexIcon.setImageResource(R.mipmap.boy);
        }else if(contract.getUsers().getSex()=="女"){
            sexIcon.setImageResource(R.mipmap.girl);
        }else {
            sexIcon.setImageResource(R.mipmap.notknow);
        }
        reputation.setText(String.valueOf(contract.getUsers().getUser_reputation()));
        details.setText("    "+contract.getDetail());
        Subtime.setText(TimeUtils.dateToString(contract.getApplyDate(),TimeUtils.FORMAT_DATE_TIME_SECOND));
        if(contract.getResult()==0){
            state.setText("未同意该申请");
        }else if(contract.getResult()==1){
            state.setText("已同意该申请");
        }else{
            state.setText("申请已失效");
        }
        connnect_phone.setText(contract.getPhoneNumber());
        connnect_place.setText(contract.getConnectPlace());
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contract.getEntrust().getStatus().equals("正常")) {
                    //判断申请的状态，若为未同意则设为同意
                    if (contract.getResult() == 0) {
                        new AlertDialog.Builder(Curcontext).setTitle("一旦选中，只有申请人能看到你的寄养信息？")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 点击“确认”后
                                        //判断寄养信息结果是否为已完结或失效
                                        SimpleHttpPostUtil httpReponse = new SimpleHttpPostUtil("applyApplication", "updateColumnsByWheres");
                                        httpReponse.addWhereParams("id", "=", contract.getId());
                                        httpReponse.addColumnParams("result", "1");
                                        httpReponse.updateColumnsByWheres(new HttpCallBack() {
                                            @Override
                                            public void Success(String data) {
                                                Toast.makeText(mContext, "已同意", Toast.LENGTH_LONG).show();
                                                state.setText("已同意");
                                                contract.setResult(1);

                                                SimpleHttpPostUtil httpReponse2 = new SimpleHttpPostUtil("entrust", "updateColumnsById");
                                                httpReponse2.addColumnParams("status", "已解决");
                                                httpReponse2.updateColumnsById(contract.getEntrust().getId(), new HttpCallBack() {
                                                    @Override
                                                    public void Success(String data) {
                                                        ;
                                                    }

                                                    @Override
                                                    public void Fail(String e) {
                                                        ;
                                                    }
                                                });
                                            }

                                            @Override
                                            public void Fail(String e) {
                                                Toast.makeText(mContext, "操作失败", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //点击“返回”后的操作,这里不设置没有任何操作
                                    }
                                }).show();

                    } else if (contract.getResult() == 1) {
                        Toast.makeText(mContext, "已经同意了", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext, "申请已失效", Toast.LENGTH_LONG).show();
                    }

                }else if(contract.getEntrust().getStatus().equals("已取消")){
                    Toast.makeText(mContext, "寄养信息已被取消了", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mContext, "已经选中过申请了", Toast.LENGTH_LONG).show();
                }
            }
        });
        return convertView;
    }
}
