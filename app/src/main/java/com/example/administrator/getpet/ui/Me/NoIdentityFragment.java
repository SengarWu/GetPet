package com.example.administrator.getpet.ui.Me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.getpet.R;
import com.example.administrator.getpet.base.BaseFragment;
import com.example.administrator.getpet.base.CustomApplication;
import com.example.administrator.getpet.utils.ToastUtils;

public class NoIdentityFragment extends BaseFragment implements View.OnClickListener {

    private View parentView;

    private Button btn_yes;
    private Button btn_no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_no_identity, container, false);
        initView();
        return parentView;
    }

    private void initView() {
        btn_yes = (Button) parentView.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(this);
        btn_no = (Button) parentView.findViewById(R.id.btn_no);
        btn_no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_yes:
                startActivity(new Intent(getActivity(),Identity1Activity.class));
                break;
            case R.id.btn_no:
                ToastUtils.showToast(CustomApplication.getmInstance(),"没有进行用户认证将不能领养宠物哦！");
                break;
        }
    }
}
