package com.example.communityfragment.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

public class MyPagerAdapter extends FragmentStateAdapter {
    public static final String TAG = "MyPagerAdapterTAG";
    private List<String> categories;

    public MyPagerAdapter(@NonNull Fragment fragment, List<String> categories) {
        super(fragment);
        this.categories = categories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = (Fragment) ARouter.getInstance()
                .build("/communityPageView/PostsFragment")
                .withString("category", categories.get(position))
                .navigation();
        return fragment;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
