package com.example.chatpageview.contract;

import com.example.chatpageview.bean.Msg;

import java.util.List;

public interface IChatContract {
    interface View {
        void addMessage(Msg msg);

        void showToast(String message);

        void removeThinkingMsg();

        void stopRequest();
    }

    interface Presenter {
        List<Msg> initMessages(int role);

        void sendMessage(String content, int role);

        void detachView();

        void cancelCurrentRequest();

        void saveToLocal(List<Msg> msgList,int role);

        void clearLocalMsg(int role);

        boolean getLoginStatus();

        void removeThinkingMsg();

        void addMessage(Msg msg);

        void stopRequest();
    }

    interface Model {
        List<Msg> initMessages(int role);

        void chat(String content, int role);

        void saveToLocal(List<Msg> msgList, int role);

        void clearLocalMsg(int role);

        boolean getLoginStatus();

        void stopRequest();
    }

}
