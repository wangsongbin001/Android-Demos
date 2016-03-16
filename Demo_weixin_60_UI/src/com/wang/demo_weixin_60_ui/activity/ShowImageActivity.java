package com.wang.demo_weixin_60_ui.activity;

import com.wang.demo_weixin_60_ui.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowImageActivity extends Activity{
	private ImageView iv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showimage);
		iv=(ImageView) findViewById(R.id.iv);
		byte[] data=getIntent().getByteArrayExtra("image");
		Bitmap bitmap=BitmapFactory.decodeByteArray(data, 0, data.length);
		iv.setImageBitmap(bitmap);
	}

}
