package com.example.chatpageview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.chatpageview.adapter.MsgAdapter;
import com.example.chatpageview.bean.Msg;
import com.example.chatpageview.contract.IChatContract;
import com.example.chatpageview.databinding.ActivityChatpageBinding;
import com.example.chatpageview.presenter.ChatPresenterImpl;
import com.example.module.libBase.AnimationUtils;
import com.example.module.libBase.SoftHideKeyBoardUtil;

import java.util.List;
import java.util.Random;

@Route(path = "/chatpageview/ChatPageActivity")
public class ChatpageActivity extends AppCompatActivity implements IChatContract.View {
    private ActivityChatpageBinding binding;
    private MsgAdapter adapter;
    private IChatContract.Presenter mPresenter;
    private List<Msg> msgList;
    private boolean isRequestInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatpageBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SoftHideKeyBoardUtil.assistActivity(this);

        mPresenter = new ChatPresenterImpl(this, this);
        msgList = mPresenter.initMessages();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        if (msgList.size() > 3) {
            layoutManager.setStackFromEnd(true);
        }
        binding.msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        binding.msgRecyclerView.setAdapter(adapter);

        binding.imgSend.setOnClickListener(v -> {
            AnimationUtils.setAnimateView(binding.imgSend);
            if (mPresenter.getLoginStatus()) {
                String content = binding.etText.getText().toString();
                if (vailInput()) {
                    binding.etText.setText("");
                    isRequestInProgress = true;
                    mPresenter.sendMessage(content);
                } else {
                    showToast("正在回答中");
                }
            } else {
                showToast("请先登录");
            }
        });

        binding.imgNewchat.setOnClickListener(v -> {
            AnimationUtils.setAnimateView(binding.imgNewchat);
            if (isRequestInProgress) {
                mPresenter.cancelCurrentRequest();
            }
            mPresenter.clearLocalMsg();
            msgList.clear();
            msgList = mPresenter.initMessages();
            binding.msgRecyclerView.setLayoutManager(new LinearLayoutManager(ChatpageActivity.this));
            adapter = new MsgAdapter(msgList);
            binding.msgRecyclerView.setAdapter(adapter);
        });
    }

    private boolean vailInput() {
        return !(msgList.get(msgList.size() - 1).getType() == Msg.TYPE_THINKING ||
                msgList.get(msgList.size() - 1).getType() == Msg.TYPE_SENT);
    }

    @Override
    public void addMessage(Msg msg) {
        runOnUiThread(() -> {
            if (msg.getType() == Msg.TYPE_RECEIVED && isRequestInProgress) {
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size() - 1);
                binding.msgRecyclerView.scrollToPosition(msgList.size() - 1);
                isRequestInProgress = false;
            } else if (msg.getType() == Msg.TYPE_SENT || msg.getType() == Msg.TYPE_THINKING) {
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size() - 1);
                binding.msgRecyclerView.scrollToPosition(msgList.size() - 1);
            }
        });
    }

    @Override
    public void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(ChatpageActivity.this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void removeThinkingMsg() {
        runOnUiThread(() -> {
            for (int i = msgList.size() - 1; i >= 0; i--) {
                if (msgList.get(i).getType() == Msg.TYPE_THINKING) {
                    msgList.remove(i);
                    adapter.notifyItemRemoved(i);
                }
            }
        });
    }

    @Override
    public void stopRequest() {
        isRequestInProgress = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.saveToLocal(msgList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        if (isRequestInProgress) {
            mPresenter.cancelCurrentRequest();
        }
    }

    private void getRandomBackground() {
        int[] backgrounds = new int[]{
                R.drawable.pic_chatbg1,
                R.drawable.pic_chatbg2,
                R.drawable.pic_chatbg3,
                R.drawable.pic_chatbg4,
                R.drawable.pic_chatbg5,
                R.drawable.pic_chatbg6
        };

        int randomIndex = new Random().nextInt(backgrounds.length);
        int selectedBackground = backgrounds[randomIndex];
        binding.msgRecyclerView.setBackgroundResource(selectedBackground);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            // 判断当前焦点是否为EditText
            if (v instanceof EditText) {
                // 将outRect设置为视图根视图坐标空间中该视图的非剪裁区域的坐标,即 EditText 在屏幕上的可见区域
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                // 触摸点不在 EditText 的可见区域
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
}
