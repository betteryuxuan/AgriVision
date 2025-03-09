package com.example.module.classificationview.contract;

import com.example.module.classificationview.base.IBaseView;
import com.example.module.libBase.bean.Crop;

import java.io.IOException;
import java.util.List;

public class IClassificationContract {

    public interface IClassificationView extends IBaseView<IClassificationPresenter> {

        void initView();
        void initListener();

        void setupFoodCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupOilCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupVegetableCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupFruitCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupWildFruitCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupSeedCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupMedicinalCatergoryRecyclerView(List<Crop.CropDetail> list);
    }

    public interface IClassificationPresenter{

        void loadCategoryDatas();

    }

    public interface IClassificationModel {

        void loadCategoryDatas(CropsCallback callback);


        interface CropsCallback {
            void onCropsLoaded(Crop data);
            void onError(IOException e);
        }
    }
}
