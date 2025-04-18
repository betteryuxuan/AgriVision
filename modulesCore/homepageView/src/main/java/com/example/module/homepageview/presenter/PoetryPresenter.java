package com.example.module.homepageview.presenter;

import com.example.module.homepageview.contract.IPoetryContract;
import com.example.module.homepageview.model.classes.Poetry;

import java.io.IOException;
import java.util.ArrayList;
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
//                if (mView != null && poetry != null) {
//                    mView.setupPoetryRecyclerView(poetry);
//                }
                poetry = new ArrayList<>();
                poetry.add(new Poetry.Item("《种豆南山下》", "陶渊明", "种豆南山下，草盛豆苗稀。\n" +
                        "晨兴理荒秽，带月荷锄归。\n" +
                        "道狭草木长，夕露沾我衣。\n" +
                        "衣沾不足惜，但使愿无违。", "我在南山下种豆，但杂草长得很旺盛，豆苗生长得很稀少。\n" +
                        "清晨我起床整理荒废的土地，带着月光回家。\n" +
                        "小路狭窄，草木繁茂，傍晚的露水沾湿了我的衣服。\n" +
                        "衣服湿了不在乎，只要我的心愿没有违背。", "这首诗反映了陶渊明归隐田园后的理想生活，表达了他对田园生活的喜爱与向往。", "种豆南山下，草盛豆苗稀。","豆：指的是大豆（学名：Glycine max），是豆科大豆属的一年生草本植物，原产于中国，已有数千年的栽培历史。它是全球重要的粮食作物之一，广泛用于食品加工、油料提取和饲料生产。大豆富含蛋白质、油脂和多种营养素，尤其是其高蛋白含量，使其成为重要的植物性蛋白来源。大豆的种子可以作为直接食用的食材，常见的加工食品有豆腐、豆浆、黄豆芽等。此外，大豆还用于提取大豆油和大豆蛋白，广泛应用于食品工业和其他领域。"));
                poetry.add(new Poetry.Item("《观刈麦》", "白居易", "田家少闲月，五月人倍忙。\n" +
                        "夜来南风起，小麦覆陇黄。\n" +
                        "妇女携家菜，蹙蹙满田仓。\n" +
                        "无语到黄昏，四顾天和地。", "农家每到五月都特别忙碌，麦子成熟时，农人夜里也不能休息。\n" +
                        "南风吹来，小麦已覆满山坡，变黄了。\n" +
                        "妇女携带着家中的菜蔬，蹙着眉头走遍了田地。\n" +
                        "他们无言地工作，直到黄昏，四周一片静谧。", "这首诗描写了农民收麦时的繁忙场景，展现了他们的辛勤劳动和生活的艰辛。", "夜来南风起，小麦覆陇黄。", "小麦：小麦（学名：Triticum aestivum）是禾本科小麦属的一年生草本植物，原产于西亚地区，已有几千年的栽培历史。小麦是全球最重要的粮食作物之一，广泛用于面粉的生产和多种食品加工。小麦富含碳水化合物、蛋白质和膳食纤维，特别适合用于制作面包、面条、饼干等各种食品。\n" +
                        "小麦的种子（麦粒）是直接食用的重要原料，经过磨粉后制成面粉，常见的加工食品有面包、饺子皮、蛋糕等。小麦还用于制作麦片、啤酒等，并广泛应用于饲料和工业生产。"));
                poetry.add(new Poetry.Item("《咏稻》", "白居易", "禾黍满田，农夫喜，犁耕休。\n" +
                        "老夫已无力，况在稻谷秋。", "稻田里满是成熟的禾黍，农夫们看着丰收而喜悦，犁耕暂时停歇。\n" +
                        "老农已力不从心，特别是秋天稻谷丰收之时。", "这首诗表达了农田丰收时的喜悦以及对劳动的感怀，表现了农民的辛勤付出和老年的感慨。", "禾黍满田，农夫喜", "禾黍：禾黍（学名：Setaria italica）是禾本科黍属的一种重要粮食作物，原产于亚洲，尤其在中国和印度有着悠久的栽培历史。禾黍的种子小且富含碳水化合物，主要用于食品加工、饲料以及一些地方的酿酒。它的耐旱性强，适应贫瘠土壤，能够在干旱和不肥沃的环境中生长，因此在许多贫困地区是重要的粮食来源。由于其生长周期短，通常在春季播种，夏秋季节收获，因此它是许多地区的传统作物之一。"));
                poetry.add(new Poetry.Item("《种豆南山下》", "陶渊明", "种豆南山下，草盛豆苗稀。\n" +
                        "晨兴理荒秽，带月荷锄归。\n" +
                        "道狭草木长，夕露沾我衣。\n" +
                        "衣沾不足惜，但使愿无违。", "我在南山下种豆，但杂草长得很旺盛，豆苗生长得很稀少。\n" +
                        "清晨我起床整理荒废的土地，带着月光回家。\n" +
                        "小路狭窄，草木繁茂，傍晚的露水沾湿了我的衣服。\n" +
                        "衣服湿了不在乎，只要我的心愿没有违背。", "这首诗反映了陶渊明归隐田园后的理想生活，表达了他对田园生活的喜爱与向往。", "种豆南山下，草盛豆苗稀。","豆：指的是大豆（学名：Glycine max），是豆科大豆属的一年生草本植物，原产于中国，已有数千年的栽培历史。它是全球重要的粮食作物之一，广泛用于食品加工、油料提取和饲料生产。大豆富含蛋白质、油脂和多种营养素，尤其是其高蛋白含量，使其成为重要的植物性蛋白来源。大豆的种子可以作为直接食用的食材，常见的加工食品有豆腐、豆浆、黄豆芽等。此外，大豆还用于提取大豆油和大豆蛋白，广泛应用于食品工业和其他领域。"));
                poetry.add(new Poetry.Item("《观刈麦》", "白居易", "田家少闲月，五月人倍忙。\n" +
                        "夜来南风起，小麦覆陇黄。\n" +
                        "妇女携家菜，蹙蹙满田仓。\n" +
                        "无语到黄昏，四顾天和地。", "农家每到五月都特别忙碌，麦子成熟时，农人夜里也不能休息。\n" +
                        "南风吹来，小麦已覆满山坡，变黄了。\n" +
                        "妇女携带着家中的菜蔬，蹙着眉头走遍了田地。\n" +
                        "他们无言地工作，直到黄昏，四周一片静谧。", "这首诗描写了农民收麦时的繁忙场景，展现了他们的辛勤劳动和生活的艰辛。", "夜来南风起，小麦覆陇黄。", "小麦：小麦（学名：Triticum aestivum）是禾本科小麦属的一年生草本植物，原产于西亚地区，已有几千年的栽培历史。小麦是全球最重要的粮食作物之一，广泛用于面粉的生产和多种食品加工。小麦富含碳水化合物、蛋白质和膳食纤维，特别适合用于制作面包、面条、饼干等各种食品。\n" +
                        "小麦的种子（麦粒）是直接食用的重要原料，经过磨粉后制成面粉，常见的加工食品有面包、饺子皮、蛋糕等。小麦还用于制作麦片、啤酒等，并广泛应用于饲料和工业生产。"));
                poetry.add(new Poetry.Item("《咏稻》", "白居易", "禾黍满田，农夫喜，犁耕休。\n" +
                        "老夫已无力，况在稻谷秋。", "稻田里满是成熟的禾黍，农夫们看着丰收而喜悦，犁耕暂时停歇。\n" +
                        "老农已力不从心，特别是秋天稻谷丰收之时。", "这首诗表达了农田丰收时的喜悦以及对劳动的感怀，表现了农民的辛勤付出和老年的感慨。", "禾黍满田，农夫喜", "禾黍：禾黍（学名：Setaria italica）是禾本科黍属的一种重要粮食作物，原产于亚洲，尤其在中国和印度有着悠久的栽培历史。禾黍的种子小且富含碳水化合物，主要用于食品加工、饲料以及一些地方的酿酒。它的耐旱性强，适应贫瘠土壤，能够在干旱和不肥沃的环境中生长，因此在许多贫困地区是重要的粮食来源。由于其生长周期短，通常在春季播种，夏秋季节收获，因此它是许多地区的传统作物之一。"));
                poetry.add(new Poetry.Item("《种豆南山下》", "陶渊明", "种豆南山下，草盛豆苗稀。\n" +
                        "晨兴理荒秽，带月荷锄归。\n" +
                        "道狭草木长，夕露沾我衣。\n" +
                        "衣沾不足惜，但使愿无违。", "我在南山下种豆，但杂草长得很旺盛，豆苗生长得很稀少。\n" +
                        "清晨我起床整理荒废的土地，带着月光回家。\n" +
                        "小路狭窄，草木繁茂，傍晚的露水沾湿了我的衣服。\n" +
                        "衣服湿了不在乎，只要我的心愿没有违背。", "这首诗反映了陶渊明归隐田园后的理想生活，表达了他对田园生活的喜爱与向往。", "种豆南山下，草盛豆苗稀。","豆：指的是大豆（学名：Glycine max），是豆科大豆属的一年生草本植物，原产于中国，已有数千年的栽培历史。它是全球重要的粮食作物之一，广泛用于食品加工、油料提取和饲料生产。大豆富含蛋白质、油脂和多种营养素，尤其是其高蛋白含量，使其成为重要的植物性蛋白来源。大豆的种子可以作为直接食用的食材，常见的加工食品有豆腐、豆浆、黄豆芽等。此外，大豆还用于提取大豆油和大豆蛋白，广泛应用于食品工业和其他领域。"));
                poetry.add(new Poetry.Item("《观刈麦》", "白居易", "田家少闲月，五月人倍忙。\n" +
                        "夜来南风起，小麦覆陇黄。\n" +
                        "妇女携家菜，蹙蹙满田仓。\n" +
                        "无语到黄昏，四顾天和地。", "农家每到五月都特别忙碌，麦子成熟时，农人夜里也不能休息。\n" +
                        "南风吹来，小麦已覆满山坡，变黄了。\n" +
                        "妇女携带着家中的菜蔬，蹙着眉头走遍了田地。\n" +
                        "他们无言地工作，直到黄昏，四周一片静谧。", "这首诗描写了农民收麦时的繁忙场景，展现了他们的辛勤劳动和生活的艰辛。", "夜来南风起，小麦覆陇黄。", "小麦：小麦（学名：Triticum aestivum）是禾本科小麦属的一年生草本植物，原产于西亚地区，已有几千年的栽培历史。小麦是全球最重要的粮食作物之一，广泛用于面粉的生产和多种食品加工。小麦富含碳水化合物、蛋白质和膳食纤维，特别适合用于制作面包、面条、饼干等各种食品。\n" +
                        "小麦的种子（麦粒）是直接食用的重要原料，经过磨粉后制成面粉，常见的加工食品有面包、饺子皮、蛋糕等。小麦还用于制作麦片、啤酒等，并广泛应用于饲料和工业生产。"));
                poetry.add(new Poetry.Item("《咏稻》", "白居易", "禾黍满田，农夫喜，犁耕休。\n" +
                        "老夫已无力，况在稻谷秋。", "稻田里满是成熟的禾黍，农夫们看着丰收而喜悦，犁耕暂时停歇。\n" +
                        "老农已力不从心，特别是秋天稻谷丰收之时。", "这首诗表达了农田丰收时的喜悦以及对劳动的感怀，表现了农民的辛勤付出和老年的感慨。", "禾黍满田，农夫喜", "禾黍：禾黍（学名：Setaria italica）是禾本科黍属的一种重要粮食作物，原产于亚洲，尤其在中国和印度有着悠久的栽培历史。禾黍的种子小且富含碳水化合物，主要用于食品加工、饲料以及一些地方的酿酒。它的耐旱性强，适应贫瘠土壤，能够在干旱和不肥沃的环境中生长，因此在许多贫困地区是重要的粮食来源。由于其生长周期短，通常在春季播种，夏秋季节收获，因此它是许多地区的传统作物之一。"));

                mView.setupPoetryRecyclerView(poetry);
            }
            @Override
            public void onFailure(IOException e) {
                e.printStackTrace();
            }
        });
    }
}
