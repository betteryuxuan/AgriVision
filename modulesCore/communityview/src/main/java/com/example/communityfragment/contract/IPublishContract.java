package com.example.communityfragment.contract;

import java.util.List;

public interface IPublishContract {
    interface View {
        void publishSuccess();

        void publishFailure();
    }

    interface Presenter {
        void publish(String content, List<String> imagePath, int communityId);
    }

    interface Model {
        void publish(String content, List<String> imagePath, int communityId, PublishCallback callback);
    }

    interface PublishCallback {
        void onSuccess();

        void onFailure();

    }

}
