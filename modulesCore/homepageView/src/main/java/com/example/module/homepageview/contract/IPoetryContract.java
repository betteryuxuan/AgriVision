package com.example.module.homepageview.contract;

import com.example.module.homepageview.base.BaseView;
import com.example.module.homepageview.model.classes.Poetry;

import java.io.IOException;
import java.util.List;

public interface IPoetryContract {

    interface IPoetryView extends BaseView<IPoetryPresenter> {

        void initView();
        void initListener();
        void setupPoetryRecyclerView(List<Poetry.Item> poetry);
    }

    interface IPoetryPresenter {
        void loadPoetryDatas();
    }

    interface IPoetryModel {
        void getPoetryDatas(PoetryCallback poetryCallback);

        interface PoetryCallback {
            void onSuccess(List<Poetry.Item> poetry);
            void onFailure(IOException e);
        }
    }
}
