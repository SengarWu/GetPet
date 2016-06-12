package com.example.administrator.getpet.ui.Home.PetCircle.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.bean.pet;
import com.example.administrator.getpet.bean.praise;
import com.example.administrator.getpet.bean.users;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.BaseListAdapter;
import com.example.administrator.getpet.ui.Home.SendAdopt.Adapter.ViewHolder;
import com.example.administrator.getpet.utils.HttpCallBack;
import com.example.administrator.getpet.utils.HttpPostUtil;
import com.example.administrator.getpet.utils.ImageDownLoader;
import com.example.administrator.getpet.utils.JSONUtil;
import com.example.administrator.getpet.utils.SimpleHttpPostUtil;
import com.example.administrator.getpet.view.RoundImageView;

import java.util.Date;
import java.util.List;

/**
 * Created by Koreleone on 2016-06-12.
 */
public class PetAdapter  extends BaseListAdapter<pet> {
    private String userId;//用户id
    public PetAdapter(Context context, List<pet> items,String userId1) {
        super(context, items);
        userId=userId1;
    }
    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.include_pet_item, null);
        }
        final pet contract=getList().get(position);
        /*
        控件的获取
         */
        RoundImageView pethead=ViewHolder.get(convertView,R.id.pet_head);
        TextView petname=ViewHolder.get(convertView,R.id.petname);
        TextView username=ViewHolder.get(convertView,R.id.username);
        TextView age=ViewHolder.get(convertView,R.id.age);
        TextView chatacter=ViewHolder.get(convertView,R.id.chatacter);
        ImageView petType=ViewHolder.get(convertView,R.id.type);
        final ImageView Praise=ViewHolder.get(convertView,R.id.pra);
        final TextView Pranum=ViewHolder.get(convertView,R.id.Pranum);
        /*
        数据的显示
         */
        ImageDownLoader.showNetImage(mContext,HttpPostUtil.getImagUrl(contract.getPhoto()),pethead,R.mipmap.home_pet_photp);
        petname.setText(contract.getName());
        username.setText(contract.getUsers().getNickName());
        age.setText(String.valueOf(contract.getUsers().getAge()));
        chatacter.setText(contract.getCharacter());
        switch (contract.getCategory().getName()){
            case "猫":
                petType.setImageResource(R.mipmap.cat_type);
                break;
            case "狗":
                petType.setImageResource(R.mipmap.type_dog);
                break;
            case "鱼":
                petType.setImageResource(R.mipmap.type_fish);
                break;
            case "其他":
                petType.setImageResource(R.mipmap.type_other);
                break;
        }
        Praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("praise","QueryCount");
                httpReponse.addWhereParams("userId","=",userId);
                httpReponse.addWhereParams("petId","=",contract.getId(),"and");
                //调用QueryList方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
                httpReponse.QueryCount(new HttpCallBack() {
                    @Override
                    public void Success(String data) {
                        if(Integer.valueOf(data)>0){
                            Toast.makeText(mContext,"已经赞过啦",Toast.LENGTH_LONG).show();
                        }else{
                            SimpleHttpPostUtil httpReponse2= new SimpleHttpPostUtil("praise","Insert");
                            praise p=new praise();
                            users u=new users();
                            u.setId(userId);
                            pet pe=new pet();
                            pe.setId(contract.getId());
                            p.setUsers(u);
                            p.setPet(pe);
                            p.setTime(new Date());
                            p.setNum(0);
                            //调用insert方法
                            httpReponse2.insert(p, new HttpCallBack() {
                                @Override
                                public void Success(String data) {
                                    Praise.setImageResource(R.mipmap.praise2);
                                    Praise.setTag("hasPraise");
                                    Toast.makeText(mContext,"已点赞",Toast.LENGTH_LONG).show();
                                    Pranum.setText(String.valueOf(Integer.valueOf(Pranum.getText().toString())+1));
                                }
                                @Override
                                public void Fail(String e) {
                                    Toast.makeText(mContext,"网络忙",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                    @Override
                    public void Fail(String e)
                    {
                        Toast.makeText(mContext,"网络忙，请稍后",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        SimpleHttpPostUtil httpReponse= new SimpleHttpPostUtil("praise","QueryCount");
        httpReponse.addWhereParams("userId","=",userId);
        httpReponse.addWhereParams("petId","=",contract.getId(),"and");
        //调用QueryList方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
        httpReponse.QueryCount(new HttpCallBack() {
            @Override
            public void Success(String data) {
                if(Integer.valueOf(data)>0){
                    Praise.setImageResource(R.mipmap.praise2);
                }else{
                    Praise.setImageResource(R.mipmap.praise);
                }
            }
            @Override
            public void Fail(String e)
            {

            }
        });
        /*
        显示点赞数
         */
        SimpleHttpPostUtil httpReponse1= new SimpleHttpPostUtil("praise","QueryCount");
        httpReponse1.addWhereParams("petId","=",contract.getId());
        //调用QueryList方法   第一个参数是页码  第二个是每页的数目   当页码为-1时表示全查询
        httpReponse1.QueryCount(new HttpCallBack() {
            @Override
            public void Success(String data) {
                Pranum.setText(String.valueOf(Integer.valueOf(data)));
            }
            @Override
            public void Fail(String e)
            {

            }
        });
        return convertView;
    }
}
