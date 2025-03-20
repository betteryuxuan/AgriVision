package com.example.communityfragment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.communityfragment.adapter.PostAdapter;
import com.example.communityfragment.bean.Post;
import com.example.communityfragment.contract.IPostsContract;
import com.example.communityfragment.databinding.FragmentPostsBinding;
import com.example.communityfragment.presenter.PostsPresenter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

@Route(path = "/communityPageView/PostsFragment")
public class PostsFragment extends Fragment implements IPostsContract.View {
    public static final String TAG = "PostsFuctionTAG";
    private PostsPresenter mPresenter;
    private FragmentPostsBinding binding;
    private PostAdapter adapter;
    @Autowired
    public String category;

    private int currentPage = 1;
    private static final int PAGE_SIZE = 10;
    private boolean isLastPage = false;

    public PostsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PostsPresenter(this, getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ARouter.getInstance().inject(this);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        if (category != null)
//            Log.d("MyPagerAdapterTAG", "onViewCreated: " + category);

        adapter = new PostAdapter(1);
//        adapter.setHasStableIds(true);
        binding.rlvPosts.setLayoutManager(manager);
        binding.rlvPosts.setAdapter(adapter);

        adapter.setOnPostActionListener(new PostAdapter.OnPostActionListener() {
            @Override
            public void onLikeClick(int postId, boolean isLiked) {
                mPresenter.votePost(postId, isLiked);
            }

            @Override
            public void onDeleteClick(int postId) {
                mPresenter.deletePost(postId);
            }
        });

        binding.swipePostsRefresh.setRefreshHeader(new ClassicsHeader(getContext()));
        binding.swipePostsRefresh.setRefreshFooter(new ClassicsFooter(getContext()));

        binding.swipePostsRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                isLastPage = false;
                binding.swipePostsRefresh.setNoMoreData(false);
                mPresenter.getData(getCommunityId(), currentPage, PAGE_SIZE);
            }
        });
        binding.swipePostsRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!isLastPage) {
                    currentPage++;
                    mPresenter.getData(getCommunityId(), currentPage, PAGE_SIZE);
                } else {
                    binding.swipePostsRefresh.finishLoadMore();
                }
            }
        });

//        mPresenter.getData(getCommunityId(), currentPage, PAGE_SIZE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getData(getCommunityId(), currentPage, PAGE_SIZE);
    }

    private int getCommunityId() {
        switch (category) {
            case "农友杂谈":
                return 1;
            case "种植交流":
                return 2;
            case "农业资讯":
                return 3;
            case "全部":
                return 0;
        }
        return 0;
    }

    @Override
    public void onDataReceived(List<Post> postList) {
        Log.d(TAG, "onDataReceived: " + postList.toString());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (postList.isEmpty() && currentPage == 1) {
                    isLastPage = true;
                    binding.swipePostsRefresh.finishLoadMoreWithNoMoreData();
                    binding.swipePostsRefresh.finishRefresh(true);
                    binding.tvPostsEmpty.setText("暂无帖子");
                    binding.rlvPosts.setVisibility(View.GONE);
                    binding.tvPostsEmpty.setVisibility(View.VISIBLE);
                } else {
                    if (currentPage == 1) {
                        binding.tvPostsEmpty.setVisibility(View.GONE);
                        binding.rlvPosts.setVisibility(View.VISIBLE);
                    }
                    binding.swipePostsRefresh.finishLoadMore(true);
                    binding.swipePostsRefresh.finishRefresh(true);
                }

                if (currentPage == 1) {
                    adapter.setData(postList);
                } else {
                    adapter.addData(postList);
                }

                if (postList.size() < PAGE_SIZE) {
                    isLastPage = true;
                    binding.swipePostsRefresh.finishLoadMoreWithNoMoreData();
                } else {
                    binding.swipePostsRefresh.finishLoadMore(true);
                }
            }
        });
    }

    @Override
    public void onDataReceivedFailure() {
        if (currentPage > 1) {
            currentPage--;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.swipePostsRefresh.finishLoadMore(true);
                binding.swipePostsRefresh.finishRefresh(true);
                if (currentPage == 1) {
                    adapter.clearData();
                    binding.tvPostsEmpty.setVisibility(View.VISIBLE);
                    binding.tvPostsEmpty.setText("加载失败");
                }
            }
        });
    }

    @Override
    public void deletePostSuccess(int postId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.removeData(postId);
            }
        });
    }
}