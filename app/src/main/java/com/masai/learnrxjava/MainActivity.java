package com.masai.learnrxjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.masai.learnrxjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setActivity(this);
    }

    public void onMapOperatorClick(View view) {
        launchActivity(MapOperatorActivity.class);
    }

    public void onFlatMapOperatorClick(View view) {
        launchActivity(FlatMapActivity.class);
    }

    public void onJustOperatorClick(View view){
        launchActivity(JustOperatorActivity.class);
    }
    private void launchActivity(Class className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

}
