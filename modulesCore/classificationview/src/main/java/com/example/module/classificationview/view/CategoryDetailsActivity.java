package com.example.module.classificationview.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.classificationview.R;
import com.example.module.classificationview.view.adapter.CategoryDetailsRecyclerViewAdapter;
import com.example.module.classificationview.view.adapter.CropCategoryRecyclerViewAdapter;
import com.example.module.libBase.bean.Crop;

import java.util.List;

@Route(path = "/classificationView/CategoryDetailsActivity")
public class CategoryDetailsActivity extends AppCompatActivity {
    private ImageView back;
    private RecyclerView cropsRecyclerView;
    private TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.classification_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initListenter();
    }

    private void initListenter() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.iv_category_back);
        cropsRecyclerView = findViewById(R.id.rv_category_details);
        title = findViewById(R.id.tv_category_title);

        Intent intent = getIntent();
        List<Crop.CropDetail> list = intent.getParcelableArrayListExtra("list");
        title.setText(intent.getStringExtra("title"));
        setupRecyclerView(list);
    }

    private void setupRecyclerView(List<Crop.CropDetail> list) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        cropsRecyclerView.setAdapter(new CategoryDetailsRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getApplicationContext()));
        cropsRecyclerView.setLayoutManager(staggeredGridLayoutManager);

    }
}
