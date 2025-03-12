package com.example.module.homepageview.presenter;

import com.example.module.homepageview.contract.IPoetryContract;
import com.example.module.homepageview.model.classes.Poetry;

import java.io.IOException;
import java.util.List;

public class PoetryPresenter implements IPoetryContract.IPoetryPresenter{

    private IPoetryContract.IPoetryView mView;
    private IPoetryContract.IPoetryModel mModel;

    public PoetryPresenter(IPoetryContract.IPoetryView mView, IPoetryContract.IPoetryModel mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void loadPoetryDatas() {
        mModel.getPoetryDatas(new IPoetryContract.IPoetryModel.PoetryCallback() {
            @Override
            public void onSuccess(List<Poetry.Item> poetry) {
                if (mView != null && poetry != null) {
                    mView.setupPoetryRecyclerView(poetry);
                }
            }
            @Override
            public void onFailure(IOException e) {
                e.printStackTrace();
            }
        });
    }
}
