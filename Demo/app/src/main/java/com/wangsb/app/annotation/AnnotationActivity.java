package com.wangsb.app.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wangsb.annotation.RouteAnnotation;
import com.wangsb.app.R;

@RouteAnnotation(name = "test1")
public class AnnotationActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
    }
}
