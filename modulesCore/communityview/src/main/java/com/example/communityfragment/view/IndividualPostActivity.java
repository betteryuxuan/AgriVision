//package com.example.communityfragment.view;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.example.communityfragment.adapter.PostAdapter;
//import com.example.communityfragment.bean.Post;
//import com.example.communityfragment.contract.ICommunityContract;
//import com.example.communityfragment.databinding.ActivityIndividualPostBinding;
//import com.example.communityfragment.presenter.CommunityPresenter;
//import com.example.eventbus.UserBaseMessageEventBus;
//import com.scwang.smart.refresh.footer.ClassicsFooter;
//import com.scwang.smart.refresh.header.ClassicsHeader;
//import com.scwang.smart.refresh.layout.api.RefreshLayout;
//import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
//import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Route(path = "/communityPageView/IndividualPostActivity")
//public class IndividualPostActivity extends AppCompatActivity implements ICommunityContract.View {
//    private static final String TAG = "IndividualPostActivityTAG";
//    private static final int TYPE = 2;
//    private ActivityIndividualPostBinding binding;
//
//    private PostAdapter adapter;
//    private UserBaseMessageEventBus userBaseMessageEventBus;
//    private CommunityPresenter mPresenter;
//
//    private List<Post> allPosts = new ArrayList<>();
//    private int currentPage = 1;
//    private final int pageSize = 10;
//
//    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
//    public void onMoonStickyEvent(UserBaseMessageEventBus userBaseMessageEventBus) {
//        this.userBaseMessageEventBus = userBaseMessageEventBus;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//
//        binding = ActivityIndividualPostBinding.inflate(getLayoutInflater());
//
//        setContentView(binding.getRoot());
//        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        mPresenter = new CommunityPresenter(this);
//
//        binding.imgIndiExit.setOnClickListener(v -> {
//            finish();
//        });
//
//
//        EventBus.getDefault().register(this);
//
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        adapter = new PostAdapter(getUserId());
//        adapter.setHasStableIds(true);
//        binding.rlvIndi.setLayoutManager(manager);
//        binding.rlvIndi.setAdapter(adapter);
//
//        adapter.setOnPostActionListener(new PostAdapter.OnPostActionListener() {
//            @Override
//            public void onLikeClick(int postId, boolean isLiked) {
//                if (isLiked) {
//                    mPresenter.unlikePost(postId, getUserId());
//                } else {
//                    mPresenter.likePost(postId, getUserId());
//                }
//            }
//
//            @Override
//            public void onDeleteClick(int postId) {
//
//                mPresenter.deletePost(postId);
//            }
//        });
//
//
//        binding.swipeIndiRefresh.setRefreshHeader(new ClassicsHeader(this));
//        binding.swipeIndiRefresh.setRefreshFooter(new ClassicsFooter(this));
//
//        binding.swipeIndiRefresh.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                currentPage = 1;
//                binding.swipeIndiRefresh.setNoMoreData(false);
//                binding.swipeIndiRefresh.finishRefresh(true);
//                mPresenter.getData(getUserId(), TYPE);
//            }
//        });
//
//        binding.swipeIndiRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                currentPage++;
//                loadCurrentGroup();
//                binding.swipeIndiRefresh.finishLoadMore(true);
//            }
//        });
//    }
//
//    private String getUserId() {
//        userBaseMessageEventBus = EventBus.getDefault().getStickyEvent(UserBaseMessageEventBus.class);
//        if (userBaseMessageEventBus != null && userBaseMessageEventBus.getUserId() != null)
//            return userBaseMessageEventBus.getUserId();
//        else return "";
//    }
//
//    @Override
//    public void updatePostLikeStatus(int postId, boolean isLiked) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                List<Post> postList = adapter.getPostList();
//                for (int i = 0; i < postList.size(); i++) {
//                    Post post = postList.get(i);
//                    if (post.getId() == postId) {
//                        post.setLiked(isLiked);
//                        adapter.notifyItemChanged(i);
//                        break;
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onDataReceived(List<Post> postList) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                binding.swipeIndiRefresh.finishRefresh(true);
//                if (postList == null || postList.isEmpty()) {
//                    binding.tvIndiEmpty.setVisibility(View.VISIBLE);
//                    binding.tvIndiEmpty.setText("当前未发布帖子");
//                    binding.rlvIndi.setVisibility(View.GONE);
//                } else {
//                    binding.tvIndiEmpty.setVisibility(View.GONE);
//                    binding.rlvIndi.setVisibility(View.VISIBLE);
////                    Collections.reverse(postList);
//
//                    allPosts.clear();
//                    allPosts.addAll(postList);
//
//                    for (Post post : postList) {
//                        mPresenter.checkLikeStatus(post.getId(), getUserId());
//                    }
//
//                    currentPage = 1;
//                    loadCurrentGroup();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void deletePostSuccess(int postId) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                for (Post post : allPosts) {
//                    if (post.getId() == postId) {
//                        allPosts.remove(post);
//                        break;
//                    }
//                }
//                adapter.setPostList(allPosts);
//            }
//        });
//    }
//
//    private void loadCurrentGroup() {
//        int startIndex = (currentPage - 1) * pageSize;
//        int endIndex = Math.min(startIndex + pageSize, allPosts.size());
//
//        if (startIndex < endIndex) {
//            List<Post> group = new ArrayList<>(allPosts.subList(startIndex, endIndex));
//            if (currentPage == 1) {
//                adapter.setPostList(group);
//            } else {
//                adapter.addPostList(group);
//            }
//        } else {
//            binding.swipeIndiRefresh.finishLoadMoreWithNoMoreData();
//        }
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (userBaseMessageEventBus != null) {
//            mPresenter.getData(getUserId(), TYPE);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (userBaseMessageEventBus != null) {
//            EventBus.getDefault().unregister(userBaseMessageEventBus);
//        }
//    }
//}