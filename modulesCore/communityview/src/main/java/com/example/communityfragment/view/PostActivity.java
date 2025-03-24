package com.example.communityfragment.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.communityfragment.R;
import com.example.communityfragment.adapter.CommentAdapter;
import com.example.communityfragment.adapter.ImageDisplayAdapter;
import com.example.communityfragment.bean.Comment;
import com.example.communityfragment.bean.Post;
import com.example.communityfragment.contract.IPostContract;
import com.example.communityfragment.databinding.ActivityPostBinding;
import com.example.communityfragment.presenter.PostPresenter;
import com.example.communityfragment.utils.TimeUtils;
import com.example.module.libBase.SPUtils;
import com.example.module.libBase.SoftHideKeyBoardUtil;
import com.example.module.libBase.TokenManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Route(path = "/communityPageView/PostActivity")
public class PostActivity extends AppCompatActivity implements IPostContract.View {
    public static final String TAG = "PostFunctionTAG";

    private ActivityPostBinding binding;
    private PostPresenter mPresenter = new PostPresenter(this);

    @Autowired(required = true)
    protected Post post;

    private CommentAdapter adapter;
    private boolean focusCommentInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ARouter.getInstance().inject(this);
        post = (Post) getIntent().getSerializableExtra("post");
        focusCommentInput = getIntent().getBooleanExtra("focusCommentInput", false);

        if (focusCommentInput) {
            Log.d(TAG, "onCreate: " + focusCommentInput);
            binding.etPostText.requestFocus();
            showKeyboard(binding.etPostText);
        }

        mPresenter.getComments(post.getId());

        binding.imgMypostBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tvMypostContent.setText(post.getContent());
//        binding.tvPostLikeCount.setText(post.getLikeConunt());
        Glide.with(this)
                .load(post.getUserAvatar())
                .placeholder(R.drawable.default_user2)
                .error(R.drawable.default_user2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.8f)
                .into(binding.imgMypostAvatar);
        binding.tvMypostUsername.setText(post.getUserName());
        binding.tvMypostCreatetime.setText(TimeUtils.getFormatTime(post.getCreatedTime()));
        binding.tvMypostReply.setText(String.format("共 %s 条回复", post.getCommentCount()));

//        binding.rlvMypostImage.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    // 根据触摸点的坐标判断是否点击到了子项上
//                    View child = binding.rlvMypostImage.findChildViewUnder(event.getX(), event.getY());
//                    if (child == null) {
//                        // 若 child 为 null，则表示触摸点不在任何子项上，即点击了空白区域
//                        binding.cvPost.performClick();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });

        String jsonImages = post.getImageUrls();
        if (!TextUtils.isEmpty(jsonImages)) {
            binding.rlvMypostImage.setVisibility(View.VISIBLE);
            List<String> imagesUrl = getImagesUrl(jsonImages);
            GridLayoutManager layoutManager;
            if (imagesUrl.size() <= 2) {
                layoutManager = new GridLayoutManager(PostActivity.this, 2);
            } else if (imagesUrl.size() == 3) {
                layoutManager = new GridLayoutManager(PostActivity.this, 3);
            } else if (imagesUrl.size() == 4) {
                layoutManager = new GridLayoutManager(PostActivity.this, 2);
            } else {
                layoutManager = new GridLayoutManager(PostActivity.this, 3);
            }
            ImageDisplayAdapter imageAdapter = new ImageDisplayAdapter(PostActivity.this, imagesUrl);
            binding.rlvMypostImage.setLayoutManager(layoutManager);
            binding.rlvMypostImage.setAdapter(imageAdapter);
        }

        binding.tvPostSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TokenManager.getLoginStatus(PostActivity.this)) {
                    Toast.makeText(PostActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                String content = binding.etPostText.getText().toString();
                if (content.trim().isEmpty()) {
                    Toast.makeText(PostActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
//                    binding.tvPostSend.setEnabled(false);
                    mPresenter.comment(post.getId(), content, null, null);
                }
            }
        });

        binding.imgMypostMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPostOwner(post)) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_share, popupMenu.getMenu());

                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.item_post1_share) {
                                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, post.getContent());
                                sendIntent.setType("text/plain");
                                Intent shareIntent = Intent.createChooser(sendIntent, "title");
                                if (sendIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                                    v.getContext().startActivity(shareIntent);
                                }
                                return true;
                            } else if (item.getItemId() == R.id.item_post1_refresh) {
                                Toast.makeText(PostActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                mPresenter.getComments(post.getId());
                            }
                            return false;
                        }
                    });
                } else {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu_share2, popupMenu.getMenu());

                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.item_post2_share) {
                                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT, post.getContent());
                                sendIntent.setType("text/plain");
                                Intent shareIntent = Intent.createChooser(sendIntent, "title");
                                if (sendIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                                    v.getContext().startActivity(shareIntent);
                                }
                                return true;
                            } else if (item.getItemId() == R.id.item_post2_delete) {
                                mPresenter.deletePost(post.getId());
                                return true;
                            } else if (item.getItemId() == R.id.item_post2_refresh) {
                                Toast.makeText(PostActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                mPresenter.getComments(post.getId());
                            }
                            return false;
                        }
                    });
                }
            }
        });

    }

    // 获取评论
    @Override
    public void onCommentsSuccess(List<Comment> comments) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (comments == null || comments.isEmpty()) {
                    binding.tvMypostEmpty.setVisibility(View.VISIBLE);
                    binding.rvMypostReply.setVisibility(View.GONE);
                } else {
                    binding.tvMypostEmpty.setVisibility(View.GONE);
                    binding.rvMypostReply.setVisibility(View.VISIBLE);

                    binding.rvMypostReply.setLayoutManager(new LinearLayoutManager(PostActivity.this));

                    adapter = new CommentAdapter(PostActivity.this, comments);
                    binding.rvMypostReply.setAdapter(adapter);

                    post.setCommentCount(String.valueOf(comments.size()));
                    binding.tvMypostReply.setText(String.format("共 %s 条回复", post.getCommentCount()));
                }
            }
        });
    }

    @Override
    public void onCommentsFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvMypostEmpty.setVisibility(View.VISIBLE);
                binding.rvMypostReply.setVisibility(View.GONE);
            }
        });
    }

    // 发步评论
    @Override
    public void onPublishCommentSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(binding.etPostText.getWindowToken(), 0);
                }

                binding.etPostText.setText("");
                mPresenter.getComments(post.getId());
                binding.tvMypostReply.setText(String.format("共 %s 条回复", post.getCommentCount()));
            }
        });
    }

    @Override
    public void onPublishCommentFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PostActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteSuccess(int postId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    private void showKeyboard(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isPostOwner(Post currentPost) {
        String username = SPUtils.getString(this, SPUtils.USERNAME_KEY, "");
        String avatar = SPUtils.getString(this, SPUtils.AVATAR_KEY, "");
        Log.d("PostAdapter", "isPostOwner: " + username + " " + avatar + " " + currentPost.getUserName() + " " + currentPost.getUserAvatar());
        return username.equals(currentPost.getUserName()) && avatar.equals(currentPost.getUserAvatar());
    }

    private List<String> getImagesUrl(String jsonImages) {
        List<String> images = new ArrayList<>();
        try {
            JSONArray object = new JSONArray(jsonImages);
            for (int i = 0; i < object.length(); i++) {
                images.add(object.getString(i));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return images;
    }
}