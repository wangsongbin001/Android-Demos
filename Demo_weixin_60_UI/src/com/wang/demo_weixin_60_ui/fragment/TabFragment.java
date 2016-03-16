package com.wang.demo_weixin_60_ui.fragment;

import com.wang.demo_weixin_60_ui.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragment extends Fragment{
	private String title;
	private TextView tv_title;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		title=getArguments().getString("title");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fg_content, null);
		tv_title=(TextView) view.findViewById(R.id.tv_title);
		tv_title.setText(title);
		return view;
	}

}
