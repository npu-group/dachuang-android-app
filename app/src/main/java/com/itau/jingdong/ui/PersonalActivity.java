package com.itau.jingdong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.itau.jingdong.R;
import com.itau.jingdong.login.LoginActivity;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.utils.CommonTools;
import com.itau.jingdong.widgets.CustomScrollView;

/**
 * @author zlei
 * 
 * @email tauchen1990@gmail.com,1076559197@qq.com
 * 
 * @date 2013年9月21日
 * 
 * @version V_1.0.0
 * 
 * @description
 * 
 */
public class PersonalActivity extends BaseActivity {

	private ImageView mBackgroundImageView = null;
	private Button mLoginButton = null;
	private CustomScrollView mScrollView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
		mBackgroundImageView = (ImageView) findViewById(R.id.personal_background_image);
		mLoginButton = (Button) findViewById(R.id.personal_login_button);
		mScrollView = (CustomScrollView) findViewById(R.id.personal_scrollView);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		mScrollView.setImageView(mBackgroundImageView);
		mLoginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i_personal = new Intent(PersonalActivity.this, LoginActivity.class);
				startActivity(i_personal);
//				CommonTools.showShortToast(PersonalActivity.this, "稍后开放");
			}
		});
	}
}
