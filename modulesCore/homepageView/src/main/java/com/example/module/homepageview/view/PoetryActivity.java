package com.example.module.homepageview.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.homepageview.R;
import com.example.module.homepageview.contract.IPoetryContract;
import com.example.module.homepageview.model.PoetryModel;
import com.example.module.homepageview.model.classes.Poetry;
import com.example.module.homepageview.presenter.PoetryPresenter;
import com.example.module.homepageview.view.adapter.PoetryDetailRecyclerViewAdapter;
import com.example.module.homepageview.view.adapter.PoetryRecyclerViewAdapter;

import java.util.List;

@Route(path = "/HomePageView/PoetryActivity")
public class PoetryActivity extends AppCompatActivity implements IPoetryContract.IPoetryView{
    private static final String TAG = "PoetryActivity";

    private IPoetryContract.IPoetryPresenter mPresenter;
    private RecyclerView recyclerView;
    private ImageButton back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poetry);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.poetry_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (mPresenter == null) {
            mPresenter = new PoetryPresenter(this, new PoetryModel(this));
        }
        recyclerView = findViewById(R.id.rv_poetry_show);
        back = findViewById(R.id.ib_poetry_details_back);

        mPresenter.loadPoetryDatas();

        initView();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                finish();
            }
        });
    }

    @Override
    public void setupPoetryRecyclerView(List<Poetry.Item> poetry) {
        recyclerView.setAdapter(new PoetryDetailRecyclerViewAdapter(poetry, new PoetryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Poetry.Item poetry) {
                ARouter.getInstance().build("/HomePageView/PoetryDetailsActivity")
                        .withOptionsCompat(ActivityOptionsCompat.makeScaleUpAnimation(
                                recyclerView, recyclerView.getWidth() / 2, recyclerView.getHeight() / 2, 0, 0
                        ))
                        .withParcelable("item", poetry)
                        .navigation(getApplicationContext());
            }
        }, getApplicationContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void setPresenter(IPoetryContract.IPoetryPresenter presenter) {
        this.mPresenter = presenter;
    }
}
