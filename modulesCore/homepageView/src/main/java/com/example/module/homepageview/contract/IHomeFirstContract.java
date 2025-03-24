package com.example.module.homepageview.contract;

import com.example.module.homepageview.base.BaseView;
import com.example.module.homepageview.model.classes.News;
import com.example.module.homepageview.model.classes.Poetry;
import com.example.module.homepageview.model.classes.Proverb;
import com.example.module.homepageview.model.classes.Recommend;
import com.example.module.libBase.bean.Crop;

import java.io.IOException;
import java.util.List;

public interface IHomeFirstContract {

    interface IHomeFirstView extends BaseView<IHomeFirstPresenter> {

        void initView();

        void initListener();

        void initAinm();


        void setupRecommendRecyclerView(List<Recommend> list);

        void setupCropRecyclerView(List<Crop.DataItem> list);

        void setupNewsRecyclerView(List<News.Item> list);

        void setupProverbViewPager(List<Proverb.ProverbData> list);

        void setupPoetryRecyclerView(List<Poetry.Item> list);
    }

    interface IHomeFirstPresenter {


        void loadRecommendRecyclerViewDatas();

        void loadCropRecyclerViewDatas();

        void loadNewsRecyclerViewDatas();

        void loadProverbViewPagerDatas();

        void loadPoetryRecyclerViewDatas();
    }

    interface IHomeFirstModel<T> {

        List<Recommend> getRecommendRecyclerViewDatas();

        void getCropRecyclerViewDatas(CropsCallback callback);

        void getNewsRecyclerViewDatas(NewsCallback callback);

        void getProverbViewPagerDatas(ProverbCallback callback);

        List<Poetry.Item> getPoetryRecyclerViewDatas();

        interface CropsCallback {
            void onCropsLoaded(List<Crop.DataItem> data);
            void onError(IOException e);
        }

        interface NewsCallback {
            void onNewsLoaded(List<News.Item> data);
            void onError(IOException e);
        }

        interface ProverbCallback {
            void onProverbsLoaded(List<Proverb.ProverbData> data);
            void onError(IOException e);
        }

    }

}
