package com.example.songbinwang.littledemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.songbinwang.littledemo.controller.PackageManagerActivity;
import com.example.songbinwang.littledemo.view.CircleRatioView;
import com.example.songbinwang.littledemo.view.TopBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TopBar mTopbar;
    private CircleRatioView ratioView;
    private Button btn_pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mTopbar = (TopBar)findViewById(R.id.topbar);
        mTopbar.setRightBtnVisibility(View.VISIBLE);
        mTopbar.setOnTopbarClickListener(new TopBar.OnTopbarClickListener() {

            @Override
            public void onLeftClick(View view) {
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onRightClick(View view) {
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_LONG).show();
                setResult(0, getIntent());
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ratioView = (CircleRatioView) findViewById(R.id.ratioview);
        ratioView.setRatio(0.8f,"同比增长80%");

        btn_pm = (Button) findViewById(R.id.btn_pm);
        btn_pm.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pm:
                startActivity(new Intent(this, PackageManagerActivity.class));
                break;
        }
    }
}
