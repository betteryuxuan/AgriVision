package com.example.personalinfoview.contract;


import android.net.Uri;

import com.example.module.libBase.bean.User;
import com.example.personalinfoview.bean.MenuItem;

import java.util.List;

public interface IInfoContract {
    interface View {
        void showUserInfo(User user);
    }

    interface Presenter {
        void setUserInfo();

        void getUser(User user);

        String getUserAvatar();
    }

    interface Model {
        void getUserInfo();

        String getUserAvatar();
    }

}
