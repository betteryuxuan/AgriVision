package com.example.personalinfoview.contract;


import android.net.Uri;

import com.example.module.libBase.bean.User;

public interface IMyInfoContract {
    interface View {
    }

    interface Presenter {
        void modifyInfo(String username, String email);

        void saveUserAvatar(Uri avatarUri);

        String getUserAvatar();

        void modifyUserAvatar(String avatarPath);

        void logout();
    }

    interface Model {
        void modifyInfo(String username, String email);

        void saveUserAvatar(String avatarUri);

        String getUserAvatar();

        void modifyUserAvatar(String avatarPath);

        void logout();
    }

}
