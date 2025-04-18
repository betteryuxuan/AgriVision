package com.example.chatpageview.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.example.chatpageview.bean.Msg;
import com.example.chatpageview.contract.IChatContract;
import com.example.chatpageview.model.ChatModelImpl;
import com.example.module.libBase.SPUtils;
import com.example.module.libBase.TimeUtils;
import com.example.module.libBase.TokenManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatPresenterImpl implements IChatContract.Presenter {
    private IChatContract.View mView;
    private IChatContract.Model mModel;
    private Context mContext;

    public ChatPresenterImpl(IChatContract.View view, Context context) {
        this.mView = view;
        this.mContext = context;
        mModel = new ChatModelImpl(this, mContext);
    }

    @Override
    public List<Msg> initMessages(int role) {
        return mModel.initMessages(role);

    }

    @Override
    public void sendMessage(String content, int role) {
        if (TextUtils.isEmpty(content)) {
            if (mView != null) {
                mView.showToast("请输入内容");
            }
            return;
        }
        Msg msg = new Msg(content, TimeUtils.getFormattedTime(), Msg.TYPE_SENT);
        if (mView != null) {
            mView.addMessage(msg);
            addThinkingMsg();
            mModel.chat(content, role);
        }
    }

    private void addThinkingMsg() {
        Runnable thinkingRunnable = new Runnable() {
            @Override
            public void run() {
                Msg thinkingMsg = new Msg("", TimeUtils.getFormattedTime(), Msg.TYPE_THINKING);
                mView.addMessage(thinkingMsg);
            }
        };

        new Handler(Looper.getMainLooper()).postDelayed(thinkingRunnable, 800);
    }


    public boolean getLoginStatus() {
        return mModel.getLoginStatus();
    }


    @Override
    public void cancelCurrentRequest() {
        mModel.stopRequest();
    }

    @Override
    public void removeThinkingMsg() {
        mView.removeThinkingMsg();
    }

    @Override
    public void addMessage(Msg msg) {
        mView.addMessage(msg);
    }

    @Override
    public void saveToLocal(List<Msg> msgList, int role) {
        mModel.saveToLocal(msgList, role);
    }

    @Override
    public void clearLocalMsg(int role) {
        mModel.clearLocalMsg(role);
    }

    @Override
    public void detachView() {
        this.mView = null;
        mModel.stopRequest();
    }

    public void stopRequest() {
        if (mView != null)
            mView.stopRequest();
    }
}