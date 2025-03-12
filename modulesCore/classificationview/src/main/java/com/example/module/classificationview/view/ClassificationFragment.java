package com.example.module.classificationview.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.classificationview.R;
import com.example.module.classificationview.contract.IClassificationContract;
import com.example.module.classificationview.model.ClassificationModel;
import com.example.module.classificationview.presenter.ClassificationPresenter;
import com.example.module.classificationview.view.adapter.CropCategoryRecyclerViewAdapter;
import com.example.module.libBase.bean.Crop;
import com.example.module.libBase.bean.SpaceItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/classificationView/ClassificationFragment")
public class ClassificationFragment extends Fragment implements IClassificationContract.IClassificationView {

    private IClassificationContract.IClassificationPresenter presenter;
    private RecyclerView foodRecyclerView, oilRecyclerView, vegetableRecyclerView, fruitRecyclerView, wildFruitRecyclerView, seedRecyclerView, medicinalRecyclerView;
    private ImageView foodButton, oilButton, vegetableButton, fruitButton, wildFruitButton, seedButton, medicinalButton;
    private Banner banner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.classification_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (presenter == null) {
            presenter = new ClassificationPresenter(this, new ClassificationModel(getContext()));
        }

        banner = view.findViewById(R.id.banner_classification_top);

        foodRecyclerView = view.findViewById(R.id.rv_category_food);
        oilRecyclerView = view.findViewById(R.id.rv_category_oil);
        vegetableRecyclerView = view.findViewById(R.id.rv_category_vegetable);
        fruitRecyclerView = view.findViewById(R.id.rv_category_fruit);
        wildFruitRecyclerView = view.findViewById(R.id.rv_category_wild_fruit);
        seedRecyclerView = view.findViewById(R.id.rv_category_seed);
        medicinalRecyclerView = view.findViewById(R.id.rv_category_medicinal);

        foodButton = view.findViewById(R.id.iv_category_food);
        oilButton = view.findViewById(R.id.iv_category_oil);
        vegetableButton = view.findViewById(R.id.iv_category_vegetable);
        fruitButton = view.findViewById(R.id.iv_category_fruit);
        wildFruitButton = view.findViewById(R.id.iv_category_wild_fruit);
        seedButton = view.findViewById(R.id.iv_category_seed);
        medicinalButton = view.findViewById(R.id.iv_category_medicinal);
        initView();
        initListener();

    }

    @Override
    public void initView() {

        presenter.loadCategoryDatas();
        presenter.loadBannerDatas();

    }

    @Override
    public void initListener() {

    }

    @Override
    public void setupBanner(List<Integer> list) {
        banner.setAdapter(new BannerImageAdapter<Integer>(list) {
                    @Override
                    public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                        holder.imageView.setImageResource(data);
                    }
                }).addBannerLifecycleObserver(this)
                .setLoopTime(3000);
    }

    @Override
    public void setupFoodCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        foodRecyclerView.setLayoutManager(linearLayoutManager);
        foodRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));
        foodRecyclerView.setNestedScrollingEnabled(false);

        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "粮食作物");
                startActivity(intent);
            }
        });
        int space = 25;
        foodRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
    }

    @Override
    public void setupOilCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        oilRecyclerView.setLayoutManager(linearLayoutManager);
        oilRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));
        oilRecyclerView.setNestedScrollingEnabled(false);

        oilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "油料作物");
                startActivity(intent);
            }
        });
        int space = 25;
        oilRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
    }

    @Override
    public void setupVegetableCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        vegetableRecyclerView.setLayoutManager(linearLayoutManager);
        vegetableRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));
        vegetableRecyclerView.setNestedScrollingEnabled(false);

        vegetableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "蔬菜作物");
                startActivity(intent);
            }
        });
        int space = 25;
        vegetableRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
    }

    @Override
    public void setupFruitCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fruitRecyclerView.setLayoutManager(linearLayoutManager);
        fruitRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));
        fruitRecyclerView.setNestedScrollingEnabled(false);

        fruitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "水果作物");
                startActivity(intent);
            }
        });
        int space = 25;
        fruitRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
    }

    @Override
    public void setupWildFruitCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        wildFruitRecyclerView.setLayoutManager(linearLayoutManager);
        wildFruitRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));
        wildFruitRecyclerView.setNestedScrollingEnabled(false);

        wildFruitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "野果作物");
                startActivity(intent);
            }
        });
        int space = 25;
        wildFruitRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
    }

    @Override
    public void setupSeedCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        seedRecyclerView.setLayoutManager(linearLayoutManager);
        seedRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));
        seedRecyclerView.setNestedScrollingEnabled(false);

        seedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "种子作物");
                startActivity(intent);
            }
        });
        int space = 25;
        seedRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
    }

    @Override
    public void setupMedicinalCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        medicinalRecyclerView.setLayoutManager(linearLayoutManager);
        medicinalRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));
        medicinalRecyclerView.setNestedScrollingEnabled(false);

        medicinalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "药草作物");
                startActivity(intent);
            }
        });
        int space = 25;
        medicinalRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
    }

    @Override
    public void setPresenter(IClassificationContract.IClassificationPresenter presenter) {

    }
}
