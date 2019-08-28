package com.wenxi.sompde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=new TextView(this);
        Toast.makeText(this, "congratulation you have successfully connent to de library", Toast.LENGTH_SHORT).show();
    }
}
