package com.example.cropdoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    public static final int TAKE_PHOTO      = 1;
    public static final int CHOOSE_PHOTO    = 2;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //Log.d("MainActivity", "MainActivity begin!");
        Button btnCam = (Button) findViewById(R.id.button_camera);
        btnCam.setOnClickListener(new BtnCamClickListener());
        Button btnPic = (Button) findViewById(R.id.button_picture);
        btnPic.setOnClickListener(new BtnPicClickListener());
        Button btnAbt = (Button) findViewById(R.id.button_about);
        btnAbt.setOnClickListener(new BtnAbtClickListener());
        Button btnUsr = (Button) findViewById(R.id.button_user);
        btnUsr.setOnClickListener(new BtnUsrClickListener());
    }

    // 相机按钮监听器
    class BtnCamClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
            try {
                if(outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(MainActivity.this,
                        "com.example.cropdoctor.fileprovider", outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }
            //隐式intent调用系统相机
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO);
        }
    }

    // 图库按钮监听器
    class BtnPicClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, CHOOSE_PHOTO);
        }
    }
    //关于按钮监听器
    class BtnAbtClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AboutActivity.activityStart(MainActivity.this);
            AboutActivity.main5();
//            AboutActivity.main8();
        }
    }

    //用户按钮监听器
    class BtnUsrClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    PreviewActivity.activityStart(MainActivity.this, imageUri.toString());
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK) {
                    imageUri = data.getData();
                    PreviewActivity.activityStart(MainActivity.this, imageUri.toString());
                }
                break;
            default:
                break;
        }
    }

}