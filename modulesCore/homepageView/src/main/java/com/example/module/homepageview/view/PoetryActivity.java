package com.example.module.homepageview.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.homepageview.R;
import com.example.module.homepageview.contract.IPoetryContract;
import com.example.module.homepageview.model.PoetryModel;
import com.example.module.homepageview.model.classes.Poetry;
import com.example.module.homepageview.presenter.PoetryPresenter;

import java.util.List;

@Route(path = "/HomePageView/PoetryActivity")
public class PoetryActivity extends AppCompatActivity implements IPoetryContract.IPoetryView{

    private IPoetryContract.IPoetryPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poetry);


        if (mPresenter == null) {
            mPresenter = new PoetryPresenter(this, new PoetryModel(this));
        }

        mPresenter.loadPoetryDatas();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void setupPoetryRecyclerView(List<Poetry.Item> poetry) {

    }

    @Override
    public void setPresenter(IPoetryContract.IPoetryPresenter presenter) {
        this.mPresenter = presenter;
    }
}
