package com.example.communityfragment;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.communityfragment.view.CommunityFragemnt;

public class TestCommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_community);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CommunityFragemnt communityFragemnt = (CommunityFragemnt) ARouter
                .getInstance()
                .build("/communityPageView/CommunityFragemnt")
                .navigation();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, communityFragemnt)
                .commit();

    }
}