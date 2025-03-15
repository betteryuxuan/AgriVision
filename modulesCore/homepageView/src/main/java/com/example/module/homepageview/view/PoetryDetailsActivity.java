package com.example.module.homepageview.view;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.homepageview.R;
import com.example.module.homepageview.model.classes.Poetry;

@Route(path = "/HomePageView/PoetryDetailsActivity")
public class PoetryDetailsActivity extends AppCompatActivity {

    private static final String TAG = "PoetryDetailsActivity";

    private TextView cropIntroduction, cropTranslation, expandText1, expandText2;
    private ImageView expandImage1, expandImage2;
    private ImageButton back;
    private LinearLayout expandButton1, expandButton2;
    private TextView title, author, content, translation, introduce, appreciation, crop;

    @Autowired
    Poetry.Item item;
    private boolean isExpanded1 = false;
    private boolean isExpanded2 = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poetry_details);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.poetry_details_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ARouter.getInstance().inject(this);

        back = findViewById(R.id.iv_poetrydetails_back);
        cropIntroduction = findViewById(R.id.poetry_details_crop_introduce);
        cropTranslation = findViewById(R.id.poetry_details_translation);
        expandButton1 = findViewById(R.id.ll_poetry_expand_crop);
        expandButton2 = findViewById(R.id.ll_poetry_expand_translation);
        expandText1 = findViewById(R.id.tv_poetry_expand_crop);
        expandText2 = findViewById(R.id.tv_poetry_expand_translation);
        expandImage1 = findViewById(R.id.iv_poetry_expand_crop);
        expandImage2 = findViewById(R.id.iv_poetry_expand_translation);
        title = findViewById(R.id.tv_poetrydetails_title);
        author = findViewById(R.id.tv_poetrydetails_author);
        crop = findViewById(R.id.poetry_details_crop);
        content = findViewById(R.id.tv_poetrydetails_text);
        translation = findViewById(R.id.poetry_details_translation);
        introduce = findViewById(R.id.poetry_details_crop_introduce);
        appreciation = findViewById(R.id.poetry_details_appreciation);

        expandButton1.setOnClickListener(v -> toggleText(cropIntroduction, expandText1, expandImage1, isExpanded1, true));
        expandButton2.setOnClickListener(v -> toggleText(cropTranslation, expandText2, expandImage2, isExpanded2, false));

        setGradientEffect(cropIntroduction, true);
        setGradientEffect(cropTranslation, false);

        initView();
        initListener();
    }

    private void initView() {

        String t = item.getTitle();
        String substring = t.substring(1, t.length() - 1);


        title.setText(substring);
        author.setText(item.getAuthor());
        content.setText(item.getContent());
        crop.setText(item.getText());
        translation.setText(item.getTrans());
        introduce.setText(item.getIntroduce());
        appreciation.setText(item.getAllusion());
    }

    private void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(
                        back, back.getWidth() / 2, back.getHeight() / 2, 0, 0
                );
                finish();
                overridePendingTransition(0, android.R.anim.fade_out);
            }
        });
    }

    private void toggleText(TextView textView, TextView expandText, ImageView expandImage, boolean isExpanded, boolean isFirstText) {
        if (isExpanded) {
            textView.setMaxLines(4);
            expandText.setText("展开全文");
            expandImage.setImageResource(R.drawable.ic_open);
            setGradientEffect(textView, isFirstText);  // 设置渐变效果
        } else {
            textView.setMaxLines(Integer.MAX_VALUE); // 展开文本
            expandText.setText("收起全文");
            expandImage.setImageResource(R.drawable.ic_up);
            removeGradientEffect(textView);  // 移除渐变效果
        }
        if (isFirstText) {
            this.isExpanded1 = !this.isExpanded1;
        } else {
            this.isExpanded2 = !this.isExpanded2;
        }
    }

    private void setGradientEffect(TextView textView, boolean isFirstText) {
        // 设置渐变效果
        LinearGradient gradient = new LinearGradient(0, 0, 0, textView.getLineHeight() * 2,
                0xFF000000, 0x50000000, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(gradient);
    }

    private void removeGradientEffect(TextView textView) {
        // 移除渐变效果
        textView.getPaint().setShader(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(
                back, back.getWidth() / 2, back.getHeight() / 2, 0, 0
        );
        finish();
        overridePendingTransition(0, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 使用 makeScaleDownAnimation 创建收回动画
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(
                back, back.getWidth() / 2, back.getHeight() / 2, 0, 0
        );

        // 调用 finish() 结束当前活动
        finish();

        // 设置退出时的动画效果，这里可以使用 fade_out 或自定义动画
        overridePendingTransition(0, android.R.anim.fade_out);
    }
}
