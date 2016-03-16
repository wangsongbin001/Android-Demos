package com.wang.demo_weixin_60_ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.wang.demo_weixin_60_ui.activity.ClipImageActivity;
import com.wang.demo_weixin_60_ui.fragment.TabFragment;
import com.wang.demo_weixin_60_ui.view.ColorChange_Iv_Tv;

public class MainActivity extends FragmentActivity {
	
	private ViewPager vp_content;//vp对象
	private FragmentPagerAdapter vpAdapter;
	
	private ArrayList<ColorChange_Iv_Tv> tabList;//tab集合
	
	private String[] fgTitles={
		"fg_One","fg_Two","fg_Three","fg_Four"	
	};
	
	

	private ArrayList<TabFragment> fgList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setOverFlowShowingAlways();
		//设置是否把项目的logo和名称显示在上面
		getActionBar().setDisplayShowHomeEnabled(false);
		
		initData();
		initView();
		registerListener();
	}

	/**
	 * 使得下面的菜单键的响应事件
	 * 和actionbar上的响应的事件一样
	 */
	private void setOverFlowShowingAlways() {
		try {
			ViewConfiguration config=ViewConfiguration.get(this);
			Field field=config.getClass().getDeclaredField("sHasPermanentMenuKey");
			field.setAccessible(true);
			field.setBoolean(config, false);
		} catch (Exception e) {
			
		}
	}

	private void initView() {
		vp_content=(ViewPager) findViewById(R.id.vp_content);
		vp_content.setAdapter(vpAdapter);	
		setUpTabView();
	}
	
	private void setUpTabView() {
		ColorChange_Iv_Tv tabview_one=(ColorChange_Iv_Tv) findViewById(R.id.tabview_one);
		ColorChange_Iv_Tv tabview_two=(ColorChange_Iv_Tv) findViewById(R.id.tabview_two);
		ColorChange_Iv_Tv tabview_three=(ColorChange_Iv_Tv) findViewById(R.id.tabview_three);
		ColorChange_Iv_Tv tabview_four=(ColorChange_Iv_Tv) findViewById(R.id.tabview_four);
		
		tabview_one.setOnClickListener(onClicker);
		tabview_two.setOnClickListener(onClicker);
		tabview_three.setOnClickListener(onClicker);
		tabview_four.setOnClickListener(onClicker);
		
		tabList=new ArrayList<ColorChange_Iv_Tv>();
		tabList.add(tabview_one);
		tabList.add(tabview_two);
		tabList.add(tabview_three);
		tabList.add(tabview_four);
		
		tabview_one.setIconAlpha(1.0f);
	}

	private void registerListener() {
		vp_content.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int positon, float positonOffSet, int positionOffsetPixels) {
				if(positonOffSet>0){
					ColorChange_Iv_Tv left=tabList.get(positon);
					ColorChange_Iv_Tv right=tabList.get(positon+1);
					left.setIconAlpha(1-positonOffSet);
					right.setIconAlpha(positonOffSet);
				}
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	View.OnClickListener onClicker=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//初始化所有tab的透明度
			reSetTabs();
			
			switch(v.getId()){
			case R.id.tabview_one:
				tabList.get(0).setIconAlpha(1.0f);
				vp_content.setCurrentItem(0, false);
				break;
			case R.id.tabview_two:
				tabList.get(1).setIconAlpha(1.0f);
				vp_content.setCurrentItem(1, false);
				break;
			case R.id.tabview_three:
				tabList.get(2).setIconAlpha(1.0f);
				vp_content.setCurrentItem(2, false);
				break;
			case R.id.tabview_four:
				tabList.get(3).setIconAlpha(1.0f);
				vp_content.setCurrentItem(3, false);
				break;
			}
		}
	};
	
	//重新初始化tab的透明度
	private void reSetTabs(){
		for(int i=0;i<tabList.size();i++){
			tabList.get(i).setIconAlpha(0);
		};
	}
	
	private void initData() {
		fgList=new ArrayList<TabFragment>();
		for(int i=0;i<fgTitles.length;i++){
			Bundle bd=new Bundle();
			bd.putString("title", fgTitles[i]);
			TabFragment tf=new TabFragment();
			tf.setArguments(bd);
			fgList.add(tf);
		}
		
		vpAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return fgList.get(arg0);
			}

			@Override
			public int getCount() {
				
				return fgList.size();
			}
		};
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
		case R.id.add_friends:
			showMsg(""+item.getTitle());
			break;
		case R.id.feedback:
			showMsg(""+item.getTitle());
			break;
		case R.id.groupchat:
			startActivity(new Intent(this,ClipImageActivity.class));
			break;
		case R.id.qrcode:
			showMsg(""+item.getTitle());
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showMsg(String msg){
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu)
	{
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null)
		{
			if (menu.getClass().getSimpleName().equals("MenuBuilder"))
			{
				try
				{
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e)
				{
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
}
