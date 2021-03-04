package com.example.importfloorplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    PhotoView photoView;
    Button button;

    Bitmap bitmap;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x1234) {
                photoView.setImageBitmap(bitmap);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoView = findViewById(R.id.map);
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