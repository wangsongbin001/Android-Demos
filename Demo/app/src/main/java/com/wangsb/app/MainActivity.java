package com.wangsb.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wangsb.app.proxy.Animal;
import com.wangsb.app.proxy.AnimalProxy;
import com.wangsb.app.proxy.Fly;
import com.wangsb.app.proxy.ProxyHandler;
import com.wangsb.app.proxy.Run;
import com.wangsb.app.retention.InjectView;
import com.wangsb.app.retention.OnClick;
import com.wangsb.app.retention.RetentionUtil;
import com.wangsb.common.util.MsgUtil;

import java.lang.annotation.Retention;
import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.btn_1)
    Button btn1;
    @InjectView(R.id.btn_2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.class.getDeclaredFields();//获取成员变量
        MainActivity.class.getDeclaredMethods();//获取成员方法
        MainActivity.class.getDeclaredConstructors();//获取构造函数


        RetentionUtil.injectView(this);
        RetentionUtil.injectEvent(this);
        btn1.setText("按钮1");
        btn2.setText("按钮2");
    }

    @OnClick({R.id.btn_1, R.id.btn_2})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_1:
                MsgUtil.showMsg(this, "click button 1");
                break;
            case R.id.btn_2:
                MsgUtil.showMsg(this, "click button 2");
                break;
        }
    }


    public void test(View view){
        Animal animal = new Animal();
//        AnimalProxy animalProxy = new AnimalProxy(animal);
        ProxyHandler handler = new ProxyHandler(animal);
        Object animalProxy = Proxy.newProxyInstance(animal.getClass().getClassLoader(),
                new Class[]{Fly.class, Run.class},
                handler);
        Fly fly = (Fly) animalProxy;
        fly.fly();
        Run run = (Run) animalProxy;
        run.run();
    }
}
