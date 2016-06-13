package com.example.administrator.getpet.ui.Home.PetCircle.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.postReply;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.BaseListAdapter;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.ViewHolder;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.utils.ImageDownLoader;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.utils.TimeUtils;
import com.example.administrator.getpet.view.RoundImageView;

import java.util.List;

/**
 * Created by Koreleone on 2016-06-12.
 */
public class AnswerAdapterWithComment  extends BaseListAdapter<postReply> {
    private String state;
    public AnswerAdapterWithComment(Context context, List<postReply> items,String s) {
        super(context, items);
        state=s;
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.include_answeritem_withcomment, null);
        }
        final postReply contract=getList().get(position);
        RoundImageView user_head= ViewHolder.get(convertView,R.id.user_head);
        TextView username=ViewHolder.get(convertView,R.id.username);
        TextView time=ViewHolder.get(convertView,R.id.time);
        final TextView content=ViewHolder.get(convertView,R.id.content);
        TextView result=ViewHolder.get(convertView,R.id.result);
        Button select=ViewHolder.get(convertView,R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state.equals("未结贴")) {
                    if (contract.getResult().equals("好评回答")) {
                        Toast.makeText(mContext, "已经给予好评啦", Toast.LENGTH_LONG).show();
                    } else {
                        SimpleHttpPostUtil httpReponse = new SimpleHttpPostUtil("postReply", "updateColumnsById");
                        httpReponse.addColumnParams("result", "好评回答");
                        httpReponse.updateColumnsById(contract.getId(), new HttpCallBack() {
                            @Override
                            public void Success(String data) {
                                SimpleHttpPostUtil httpReponse2 = new SimpleHttpPostUtil("users", "updateColumnsById");
                                httpReponse2.addColumnParams("user_reputation", String.valueOf(contract.getUsers().getUser_reputation() + contract.getPost().getIntergen()));
                                httpReponse2.updateColumnsById(contract.getUsers().getId(), new HttpCallBack() {
                                    @Override
                                    public void Success(String data) {
                                        Toast.makeText(mContext, "积分已悬赏", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void Fail(String e) {

                                    }
                                });
                            }

                            @Override
                            public void Fail(String e) {
                                Toast.makeText(mContext, "网络忙，请稍后再试", Toast.LENGTH_LONG).show();
                            }
                        });

                        SimpleHttpPostUtil httpReponse3 = new SimpleHttpPostUtil("post", "updateColumnsById");
                        httpReponse3.addColumnParams("state", "已结帖");
                        httpReponse3.updateColumnsById(contract.getPost().getId(), new HttpCallBack() {
                            @Override
                            public void Success(String data) {
                                Toast.makeText(mContext, "已结帖", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void Fail(String e) {

                            }
                        });
                    }
                }else{
                    Toast.makeText(mContext, "已选过好评答案", Toast.LENGTH_LONG).show();
                }
            }
        });
        username.setText(contract.getUsers().getNickName());
        time.setText(TimeUtils.dateToString(contract.getTime(),TimeUtils.FORMAT_DATE)+":");
        content.setText("    "+contract.getMessage());
        ImageDownLoader.showNetImage(mContext, HttpPostUtil.getImagUrl(contract.getUsers().getPhoto()),user_head,R.mipmap.home_pet_photp);
        if(contract.getResult().equals("好评回答")){
            result.setText("好评回答");
        }
        return convertView;
    }
}
