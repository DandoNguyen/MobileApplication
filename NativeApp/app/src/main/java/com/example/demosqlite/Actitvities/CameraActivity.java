package com.example.demosqlite.Actitvities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demosqlite.R;

import java.io.ByteArrayOutputStream;

public class CameraActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    ImageView imageView;
    ImageButton btn_captureImage, btn_acceptImage;
    Uri imageUri;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageView = findViewById(R.id.iv_camera_view);
        btn_acceptImage = findViewById(R.id.btn_accept_image);
        btn_captureImage = findViewById(R.id.btn_take_image);


        btn_acceptImage.setOnClickListener(view -> {
            if (imageBitmap == null){
                Toast.makeText(CameraActivity.this, "Image not found", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(CameraActivity.this, FormActivity.class);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                i.putExtra("imageByteArray", bos.toByteArray());
                i.putExtra("imageURI", imageUri.toString());
                finish();
                startActivity(i);
            }

        });

        btn_captureImage.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permission, REQUEST_CODE);
                } else {
                    openCamera();
                }
            } else {
                openCamera();
            }
        });

    }

    private void openCamera() {

        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "Trip Image");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, 1001);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                } else {
                    Toast.makeText(CameraActivity.this, getString(R.string.permissionDenied), Toast.LENGTH_SHORT).show();
                }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {
            //imageView.setImageURI(imageUri);
            grabImage(imageView);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void grabImage(ImageView imageView)
    {
        this.getContentResolver().notifyChange(imageUri, null);
        ContentResolver cr = this.getContentResolver();
        try
        {
            imageBitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, imageUri);
            imageView.setImageBitmap(imageBitmap);
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}