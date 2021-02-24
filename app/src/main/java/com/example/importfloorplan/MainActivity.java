package com.example.importfloorplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;

    Bitmap bitmap;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x1234) {
                imageView.setImageBitmap(bitmap);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.test);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ChooseImageActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        //Download image from url and display in imageView
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    // Define URL
                    URL url = new URL("https://firebasestorage.googleapis.com/v0/b/floorplan-dc25f.appspot.com/o/download.jpg?alt=media&token=be62b98e-dff1-4135-b234-8951a4b0d66d");
                    // Open the input stream
                    InputStream is = url.openStream();
                    // Get image bitmap from inputstream
                    bitmap = BitmapFactory.decodeStream(is);
                    // Send message to handler
                    handler.sendEmptyMessage(0x1234);
                    is.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

        //Test intent with extra value
        Intent intent = getIntent();
        System.out.println("Test intent: "+intent.toString());
        if (intent!=null){
            boolean test = intent.getBooleanExtra("Test", false);
            if (test==true){
                thread.start();
            }
        }
    }

}