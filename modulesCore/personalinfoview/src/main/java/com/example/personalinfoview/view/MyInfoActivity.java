package com.example.personalinfoview.view;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.module.libBase.bean.User;
import com.example.personalinfoview.FileUtils;
import com.example.personalinfoview.R;
import com.example.personalinfoview.contract.IMyInfoContract;
import com.example.personalinfoview.databinding.ActivityInfoBinding;
import com.example.personalinfoview.presenter.MyInfoPresenter;
import com.yalantis.ucrop.UCrop;

import java.io.File;

@Route(path = "/personalinfoview/MyInfoActivity")
public class MyInfoActivity extends AppCompatActivity implements IMyInfoContract.View {
    private static final String TAG = "MyInfoActivityTAG";

    private ActivityInfoBinding binding;
    private MyInfoPresenter mPresenter;

    @Autowired
    public User user;
    private Uri cameraImageUri;
    private ActivityResultLauncher<Uri> cameraLauncher;
    private ActivityResultLauncher<String> cameraPermissionLauncher;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private AlertDialog.Builder builder;
    private AlertDialog imageDialog;
    private AlertDialog dialogPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInfoBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.info_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mPresenter = new MyInfoPresenter(this);

        ARouter.getInstance().inject(this);
        if (user == null) {
            Log.d("MyInfoActivity", "user: null");
        }

        if (user != null) {
            binding.tvInfoMail.setText(user.getEmail());
            binding.tvInfoUsername.setText(user.getUserName());
            String avatarUri = user.getAvatar();
            if (avatarUri != null) {
                Log.d(TAG, "有图片 " + avatarUri);
                Glide.with(this)
                        .load(avatarUri)
                        .error(R.drawable.default_user2)
                        .fallback(R.drawable.default_user2)
                        .into(binding.imgInfoPhoto);
            } else {
                Log.d(TAG, "无图片: " + avatarUri);
                Glide.with(this)
                        .load(R.drawable.default_user2)
                        .into(binding.imgInfoPhoto);
            }
        } else {
            binding.tvInfoMail.setText("未登录");
            binding.tvInfoUsername.setText("未登录");
        }

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("InfoActivity1", "onClick: ");
                finish();
            }
        });

        binding.rlUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    showUpdateUsernameDialog();
                } else {
                    Toast.makeText(MyInfoActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.rlChangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    ARouter.getInstance().build("/login/ForgetActivity")
                            .withString("email", user.getEmail())
                            .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                            .navigation();
                } else {
                    Toast.makeText(MyInfoActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 注册拍照权限请求
        cameraPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                launchCamera();
            } else {
                Toast.makeText(MyInfoActivity.this, "请允许摄像头权限以拍照", Toast.LENGTH_SHORT).show();
            }
        });

        // 注册拍照Launcher
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
            if (result) {
                // 裁剪
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                options.setCompressionQuality(100);
                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
                UCrop.of(cameraImageUri, destinationUri)
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(512, 512)
                        .withOptions(options)
                        .start(MyInfoActivity.this);
            }
        });

        // 相册选择
        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                // 获取 ContentResolver 实例 ，持久化URI访问权限
                ContentResolver contentResolver = getApplicationContext().getContentResolver();
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                // 配置uCrop
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG); // 压缩格式
                options.setCompressionQuality(100); // 压缩质量

                // 构造裁剪后图片的保存地址
                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));

                // 启动 uCrop 裁剪活动，并传入配置选项
                UCrop.of(uri, destinationUri)
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(512, 512)
                        .withOptions(options)
                        .start(MyInfoActivity.this);
            }
        });

        // 修改头像
        binding.rlPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    Toast.makeText(MyInfoActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    setImageDialog();
                }
            }
        });

        binding.rlExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    Toast.makeText(MyInfoActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    logout();
                } else {
                    LayoutInflater inflater = LayoutInflater.from(MyInfoActivity.this);
                    View customView = inflater.inflate(R.layout.dialog_logout_layout, null);
                    AlertDialog dialog = new AlertDialog.Builder(MyInfoActivity.this)
                            .setView(customView)
                            .create();
                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.default_dialog_background);
                    Button btnConfirm = customView.findViewById(R.id.btn_logout_confirm);
                    Button btnCancel = customView.findViewById(R.id.btn_logout_cancel);

                    btnConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logout();
                            dialog.dismiss();
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    // 选择更换图片方式
    private void showSelectImageSourceDialog() {
//        new AlertDialog.Builder(MyInfoActivity.this,R.style.CustomAlertDialogTheme)
//                .setTitle("选择图片来源")
//                .setItems(new String[]{"拍照", "从相册选择"}, (dialog, which) -> {
//                    if (which == 0) { // 拍照
//                        if (ContextCompat.checkSelfPermission(MyInfoActivity.this, Manifest.permission.CAMERA)
//                                != PackageManager.PERMISSION_GRANTED) {
//                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
//                        } else {
//                            launchCamera();
//                        }
//                    } else if (which == 1) { // 相册
//                        requestPermissions();
//                    }
//                }).show();

        builder = new AlertDialog.Builder(MyInfoActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_pick_layout, null);
        Button btnCamera = dialogView.findViewById(R.id.btn_camera);
        Button btnGallery = dialogView.findViewById(R.id.btn_gallery);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.dismiss();
                if (ContextCompat.checkSelfPermission(MyInfoActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
                } else {
                    launchCamera();
                }
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.dismiss();
                requestPermissions();
            }
        });

        builder.setView(dialogView);
        dialogPick = builder.create();
        dialogPick.getWindow().setBackgroundDrawableResource(R.drawable.default_dialog_background);
//        imageDialog.dismiss();
        dialogPick.show();
    }

    // 展示当前照片
    private void setImageDialog() {
        String imageUri = mPresenter.getUserAvatar();

        builder = new AlertDialog.Builder(MyInfoActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_image_viewer, null);
        ImageView photoView = dialogView.findViewById(R.id.photo_view_dialog);
        Button changeBtn = dialogView.findViewById(R.id.btn_change);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectImageSourceDialog();
            }
        });

        Glide.with(MyInfoActivity.this)
                .load(imageUri)
                .error(R.drawable.default_user2)
                .into(photoView);

        builder.setView(dialogView);
        imageDialog = builder.create();
        imageDialog.getWindow().setBackgroundDrawableResource(R.drawable.default_dialog_background);
        imageDialog.show();

        photoView.setOnClickListener(v1 -> imageDialog.dismiss());
    }

    // 打开相机
    private void launchCamera() {
        // 创建用于存储拍照图片的临时文件，需要配置FileProvider，在res/xml下添加fileprovider_paths.xml文件
        File imageFile = new File(getCacheDir(), "camera_" + System.currentTimeMillis() + ".jpg");
        cameraImageUri = androidx.core.content.FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", imageFile);
        cameraLauncher.launch(cameraImageUri);
    }

    // 拍照和相册裁剪选择回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                // 更新头像
                binding.imgInfoPhoto.setImageURI(resultUri);

                // 存本地
//                mPresenter.saveUserAvatar(resultUri);

                // 存网络
                String realPath = FileUtils.getRealPathFromUri(MyInfoActivity.this, resultUri);
                mPresenter.modifyUserAvatar(realPath);

                // 如果原来的 dialog 正在显示，则关闭它
                if (imageDialog != null && imageDialog.isShowing()) {
                    imageDialog.dismiss();
                }
                if (dialogPick != null && dialogPick.isShowing()) {
                    dialogPick.dismiss();
                }
//                setImageDialog();
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Throwable cropError = UCrop.getError(data);
            Toast.makeText(this, "裁剪出错：图片格式不支持", Toast.LENGTH_SHORT).show();
            Log.e("MyInfoActivityTAG", "onActivityResult: " + cropError);
        }
    }

    // 相册权限
    private ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                boolean granted = true;
                for (Boolean value : result.values()) {
                    if (!value) {
                        granted = false;
                        break;
                    }
                }
                if (granted) {
                    launchImagePicker();
                } else {
                    Toast.makeText(MyInfoActivity.this, "请允许权限以选择头像", Toast.LENGTH_SHORT).show();
                }
            });


    // 根据版本判断请求权限来打开相册
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissionLauncher.launch(new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            });
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
            });
        } else {
            permissionLauncher.launch(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            });
        }
    }

    // 从相册选择图片
    private void launchImagePicker() {
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    // 修改用户名
    private void showUpdateUsernameDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_username_layout, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.default_dialog_background);
        dialog.show();


        EditText usernameEt = dialogView.findViewById(R.id.et_dialog_email);
        Button confiemBtn = dialogView.findViewById(R.id.btn_dialog_confirm);
        Button canceltBtn = dialogView.findViewById(R.id.btn_dialog_cancel);

        canceltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confiemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEt.getText().toString();
                if (username.isEmpty()) {
                    usernameEt.setError("用户名不能为空");
                } else if (username.length() > 10) {
                    usernameEt.setError("用户名长度不能大于10");
                } else {
                    mPresenter.modifyInfo(username, user.getEmail());
                    dialog.dismiss();
                }
            }
        });
    }

    public void onModifyInfoFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onModifyInfoSuccess(String username) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tvInfoUsername.setText(username);
                Toast.makeText(MyInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                finishAffinity();
                ARouter.getInstance().build("/main/MainActivity")
                        .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                        .navigation();
            }
        });
    }


    private void logout() {
        mPresenter.logout();
        finishAffinity();

        ARouter.getInstance()
                .build("/login/LoginActivity")
                .navigation();
    }

}