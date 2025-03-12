package com.example.module.homepageview.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.module.homepageview.R;
import com.example.module.homepageview.contract.IHomeFirstContract;
import com.example.module.homepageview.model.classes.News;
import com.example.module.homepageview.model.classes.Poetry;
import com.example.module.homepageview.model.classes.Proverb;
import com.example.module.homepageview.model.classes.Recommend;
import com.example.module.homepageview.presenter.HomePagePresenter;
import com.example.module.libBase.TokenManager;
import com.example.module.libBase.bean.Crop;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeFirstModel implements IHomeFirstContract.IHomeFirstModel<HomePagePresenter> {

    private static final String CROP_URL = "http://101.200.122.3:8080/firstpage/crop";
    private static final String PROVERB_URL = "http://101.200.122.3:8080/firstpage/proverb";
    private static final String NEWS_URL = "http://101.200.122.3:8080/firstpage/news";

    private Context mContext;
    private static final String TAG = "HomeFirstModel";

    public HomeFirstModel(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public List<Integer> getBannerDatas() {

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.xiaomai_img);
        list.add(R.drawable.shuidao_img);

        return list;
    }

    @Override
    public List<Recommend> getRecommendRecyclerViewDatas() {
        List<Recommend> list = new ArrayList<>();
        list.add(new Recommend(R.drawable.gufeng_img1, "作物分类", ""));
        list.add(new Recommend(R.drawable.gufeng_img2, "诗词作物", ""));
        list.add(new Recommend(R.drawable.gufeng_img3, "知识储备", ""));
        list.add(new Recommend(R.drawable.gufeng_img4, "视频经验", ""));
        return list;
    }

    @Override
    public void getCropRecyclerViewDatas(CropsCallback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String token = TokenManager.getToken(mContext);
        Log.d(TAG, "Token为：" + token);

        Request.Builder builder = new Request.Builder();
        builder.url(CROP_URL);
        if (token != null) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "请求失败", e);
                if (callback != null) {
                    callback.onError(e);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "请求失败，状态码：" + response.code());
                    if (callback != null) {
                        callback.onError(new IOException("请求失败，状态码：" + response.code()));
                    }
                    return;
                }

                ResponseBody body = response.body();
                if (body == null) {
                    Log.e(TAG, "响应体为空");
                    if (callback != null) {
                        callback.onError(new IOException("响应体为空"));
                    }
                    return;
                }

                String responseBody = body.string();
                Log.d(TAG, "解析前的数据：" + responseBody);

                Gson gson = new Gson();
                Crop crop = gson.fromJson(responseBody, Crop.class);
                Log.d(TAG, "解析后的数据：" + crop);

                // 确保回调在主线程中执行
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onCropsLoaded(crop.getData()));
                }
            }

        });
    }

    @Override
    public void getNewsRecyclerViewDatas(NewsCallback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String token = TokenManager.getToken(mContext);
        Log.d(TAG, "Token为：" + token);

        Request.Builder builder = new Request.Builder();
        builder.url(NEWS_URL);
        if (token != null) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "请求失败", e);
                if (callback != null) {
                    callback.onError(e);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "请求失败，状态码：" + response.code());
                    if (callback != null) {
                        callback.onError(new IOException("请求失败，状态码：" + response.code()));
                    }
                    return;
                }

                ResponseBody body = response.body();
                if (body == null) {
                    Log.e(TAG, "响应体为空");
                    if (callback != null) {
                        callback.onError(new IOException("响应体为空"));
                    }
                    return;
                }

                String responseBody = body.string();
                Log.d(TAG, "解析前的数据：" + responseBody);

                Gson gson = new Gson();
                News news = gson.fromJson(responseBody, News.class);
                Log.d(TAG, "解析后的数据：" + news);

                // 确保回调在主线程中执行
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onNewsLoaded(news.getData()));
                }
            }

        });
    }


    @Override
    public void getProverbViewPagerDatas(ProverbCallback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String token = TokenManager.getToken(mContext);
        Log.d(TAG, "Token为：" + token);

        Request.Builder builder = new Request.Builder();
        builder.url(PROVERB_URL);
        if (token != null) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "请求失败", e);
                if (callback != null) {
                    callback.onError(e);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "请求失败，状态码：" + response.code());
                    if (callback != null) {
                        callback.onError(new IOException("请求失败，状态码：" + response.code()));
                    }
                    return;
                }

                ResponseBody body = response.body();
                if (body == null) {
                    Log.e(TAG, "响应体为空");
                    if (callback != null) {
                        callback.onError(new IOException("响应体为空"));
                    }
                    return;
                }

                String responseBody = body.string();
//                Log.d(TAG, "解析前的数据：" + responseBody);

                Gson gson = new Gson();
                Proverb proverb = gson.fromJson(responseBody, Proverb.class);
//                Log.d(TAG, "解析后的数据：" + proverb);

                // 确保回调在主线程中执行
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onProverbsLoaded(proverb.getData()));
                }
            }

        });
    }

    @Override
    public List<Poetry.Item> getPoetryRecyclerViewDatas() {
        List<Poetry.Item> poetryList = new ArrayList<>();
        poetryList.add(new Poetry.Item("—《种豆南山下》", "陶渊明", "种豆南山下，草盛豆苗稀。\n" +
                "晨兴理荒秽，带月荷锄归。\n" +
                "道狭草木长，夕露沾我衣。\n" +
                "衣沾不足惜，但使愿无违。", "我在南山下种豆，但杂草长得很旺盛，豆苗生长得很稀少。\n" +
                "清晨我起床整理荒废的土地，带着月光回家。\n" +
                "小路狭窄，草木繁茂，傍晚的露水沾湿了我的衣服。\n" +
                "衣服湿了不在乎，只要我的心愿没有违背。", "这首诗反映了陶渊明归隐田园后的理想生活，表达了他对田园生活的喜爱与向往。", "种豆南山下，草盛豆苗稀。"));
        poetryList.add(new Poetry.Item("—《观刈麦》", "白居易", "田家少闲月，五月人倍忙。\n" +
                "夜来南风起，小麦覆陇黄。\n" +
                "妇女携家菜，蹙蹙满田仓。\n" +
                "无语到黄昏，四顾天和地。", "农家每到五月都特别忙碌，麦子成熟时，农人夜里也不能休息。\n" +
                "南风吹来，小麦已覆满山坡，变黄了。\n" +
                "妇女携带着家中的菜蔬，蹙着眉头走遍了田地。\n" +
                "他们无言地工作，直到黄昏，四周一片静谧。", "这首诗描写了农民收麦时的繁忙场景，展现了他们的辛勤劳动和生活的艰辛。", "夜来南风起，小麦覆陇黄。"));
        poetryList.add(new Poetry.Item("—《咏稻》", "白居易", "禾黍满田，农夫喜，犁耕休。\n" +
                "老夫已无力，况在稻谷秋。", "稻田里满是成熟的禾黍，农夫们看着丰收而喜悦，犁耕暂时停歇。\n" +
                "老农已力不从心，特别是秋天稻谷丰收之时。", "这首诗表达了农田丰收时的喜悦以及对劳动的感怀，表现了农民的辛勤付出和老年的感慨。", "禾黍满田，农夫喜"));
        return poetryList;
    }

}
