package com.example.personalinfoview.presenter;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.libBase.bean.User;
import com.example.personalinfoview.R;
import com.example.personalinfoview.bean.MenuItem;
import com.example.personalinfoview.contract.IInfoContract;
import com.example.personalinfoview.model.PersonalInfoModel;
import com.example.personalinfoview.view.PersonalInfoFragment;

import java.util.ArrayList;
import java.util.List;

public class PersonalInfoPresenter implements IInfoContract.Presenter {

    private final PersonalInfoFragment mView;
    private final PersonalInfoModel mModel;

    public PersonalInfoPresenter(PersonalInfoFragment view) {
        mView = view;
        mModel = new PersonalInfoModel(this, mView.getContext());
    }

    @Override
    public void setUserInfo() {
        mModel.getUserInfo();
    }

    @Override
    public void getUser(User user) {
        mView.showUserInfo(user);
    }

    @Override
    public String getUserAvatar() {
        return mModel.getUserAvatar();
    }

    public void updateUserInfo(User user) {
        mView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.UpdateUserInfo(user);
            }
        });
    }

}
