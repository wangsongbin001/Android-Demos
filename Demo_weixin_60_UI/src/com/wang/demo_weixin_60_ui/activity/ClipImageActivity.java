package com.wang.demo_weixin_60_ui.activity;

import java.io.ByteArrayOutputStream;

import com.wang.demo_weixin_60_ui.R;
import com.wang.demo_weixin_60_ui.view.ClipImageBorderView;
import com.wang.demo_weixin_60_ui.view.ClipImageLayout;
import com.wang.demo_weixin_60_ui.view.ZoomImageView2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

public class ClipImageActivity extends Activity{
	
	private ClipImageBorderView v_border;
	private ZoomImageView2 iv;
	private ClipImageLayout rl_clipimage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clipimage);
		rl_clipimage=(ClipImageLayout) findViewById(R.id.rl_clipimage);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.clip, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id=item.getItemId();
		switch(id){
		case R.id.clip_cut:
			Bitmap bitmap=rl_clipimage.clip();
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100, bos);
			byte[] data=bos.toByteArray();
			Intent intent=new Intent(this,ShowImageActivity.class);
			intent.putExtra("image", data);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
