package com.example.personalinfoview.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

public class MyVPAdapter extends FragmentStateAdapter {
    private List<String> categories;

    public MyVPAdapter(@NonNull Fragment fragment, List<String> categories) {
        super(fragment);
        this.categories = categories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String category = categories.get(position);
        if (position == 0) {
            return (Fragment) ARouter.getInstance()
                    .build("/communityPageView/PostsFragment")
                    .withString("category", category)
                    .navigation();
        } else if (position == 1) {
            return (Fragment) ARouter.getInstance()
                    .build("/communityPageView/PostsFragment")
                    .withString("category", category)
                    .navigation();
        } else if (position == 2) {
            return (Fragment) ARouter.getInstance()
                    .build("/personalinfoview/MyFavoritesFragment")
                    .navigation();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
