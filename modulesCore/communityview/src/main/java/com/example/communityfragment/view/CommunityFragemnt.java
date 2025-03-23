package com.example.communityfragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.communityfragment.adapter.MyPagerAdapter;
import com.example.communityfragment.databinding.FragmentCommunityBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

@Route(path = "/communityPageView/CommunityFragment")
public class CommunityFragemnt extends Fragment {
    private static final String TAG = "CommunityFragemntTAG";
    private FragmentCommunityBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCommunityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> titles = List.of("全部", "热榜", "农友杂谈", "种植交流", "农业资讯");

        MyPagerAdapter adapter = new MyPagerAdapter(this, titles);
        binding.vpCommunity.setAdapter(adapter);
        binding.vpCommunity.setOffscreenPageLimit(1);

        new TabLayoutMediator(binding.tbCommunity, binding.vpCommunity,
                (tab, position) -> tab.setText(titles.get(position))
        ).attach();

        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/communityPageView/PublishActivity")
                        .navigation();
            }
        });

//        binding.tbCommunity.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                binding.tbCommunity.setScrollPosition(tab.getPosition(), 0, true);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

    }


}