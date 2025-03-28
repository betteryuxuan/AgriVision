package com.example.module.classificationview.presenter;

import com.example.module.classificationview.contract.IClassificationContract;
import com.example.module.libBase.bean.Crop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassificationPresenter implements IClassificationContract.IClassificationPresenter {

    private static final String TAG = "ClassificationPresenter";

    private IClassificationContract.IClassificationView mView;
    private IClassificationContract.IClassificationModel mModel;

    public ClassificationPresenter(IClassificationContract.IClassificationView mView, IClassificationContract.IClassificationModel mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Override
    public void loadBannerDatas() {
        if (mView != null) {
            mView.setupBanner(mModel.getBannerDatas());
        }
    }

    @Override
    public void loadCategoryDatas() {
        mModel.loadCategoryDatas(new IClassificationContract.IClassificationModel.CropsCallback() {
            @Override
            public void onCropsLoaded(Crop data) {

                if (data == null || data.getData() == null) {
                    return;
                }
                List<Crop.CropDetail> foodCategoryData = new ArrayList<>();
                List<Crop.CropDetail> oilCategoryData = new ArrayList<>();
                List<Crop.CropDetail> vegetableCategoryData = new ArrayList<>();
                List<Crop.CropDetail> fruitCategoryData = new ArrayList<>();
                List<Crop.CropDetail> wildFruitCategoryData = new ArrayList<>();
                List<Crop.CropDetail> seedCategoryData = new ArrayList<>();
                List<Crop.CropDetail> medicinalCategoryData = new ArrayList<>();
                for (Crop.DataItem dataItem : data.getData()) {
                    switch (dataItem.getCategory()) {
                        case "粮食作物":
                            foodCategoryData = dataItem.getCropDetail();
                            break;
                        case "油料作物":
                            oilCategoryData = dataItem.getCropDetail();
                            break;
                        case "蔬菜作物":
                            vegetableCategoryData = dataItem.getCropDetail();
                            break;
                        case "果类":
                            fruitCategoryData = dataItem.getCropDetail();
                            break;
                        case "野生果类":
                            wildFruitCategoryData = dataItem.getCropDetail();
                            break;
                        case "饲料作物":
                            seedCategoryData = dataItem.getCropDetail();
                            break;
                        case "药用作物":
                            medicinalCategoryData = dataItem.getCropDetail();
                            break;
                        default:
                            break;
                    }
                }
                List<Crop.CropDetail> foodCategoryDatas = foodCategoryData;
                foodCategoryDatas.addAll(foodCategoryData);
                foodCategoryDatas.addAll(foodCategoryData);
                List<Crop.CropDetail> oilCategoryDatas = oilCategoryData;
                oilCategoryDatas.addAll(oilCategoryData);
                oilCategoryDatas.addAll(oilCategoryData);
                List<Crop.CropDetail> fruitCategoryDatas = fruitCategoryData;
                fruitCategoryDatas.addAll(fruitCategoryData);
                fruitCategoryDatas.addAll(fruitCategoryData);
                List<Crop.CropDetail> wildFruitCategoryDatas = wildFruitCategoryData;
                wildFruitCategoryDatas.addAll(wildFruitCategoryData);
                wildFruitCategoryDatas.addAll(wildFruitCategoryData);
                List<Crop.CropDetail> vegetableCategoryDatas = vegetableCategoryData;
                vegetableCategoryDatas.addAll(vegetableCategoryData);
                vegetableCategoryDatas.addAll(vegetableCategoryData);
                List<Crop.CropDetail> seedCategoryDatas = seedCategoryData;
                seedCategoryDatas.addAll(seedCategoryData);
                seedCategoryDatas.addAll(seedCategoryData);
                List<Crop.CropDetail> medicinalCategoryDatas = medicinalCategoryData;
                medicinalCategoryDatas.addAll(medicinalCategoryData);
                medicinalCategoryDatas.addAll(medicinalCategoryData);
                mView.setupFoodCatergoryRecyclerView(foodCategoryData);
                mView.setupOilCatergoryRecyclerView(oilCategoryData);
                mView.setupVegetableCatergoryRecyclerView(vegetableCategoryData);
                mView.setupFruitCatergoryRecyclerView(fruitCategoryData);
                mView.setupWildFruitCatergoryRecyclerView(wildFruitCategoryData);
                mView.setupSeedCatergoryRecyclerView(seedCategoryData);
                mView.setupMedicinalCatergoryRecyclerView(medicinalCategoryData);
            }

            @Override
            public void onError(IOException e) {

            }
        });
    }
}
