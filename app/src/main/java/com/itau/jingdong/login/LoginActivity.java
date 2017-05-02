package com.itau.jingdong.login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itau.jingdong.R;
import com.itau.jingdong.global.Global;
import com.itau.jingdong.ui.HomeActivity;

/**登陆界面activity*/
public class LoginActivity extends Activity implements OnClickListener{
	/**更多登陆项的显示View*/
//	private View view_more;
	/**更多菜单，默认收起，点击后展开，再点击收起*/
//	private View menu_more;
//	private ImageView img_more_up;//更多登陆项箭头图片
	private Button btn_login_regist;//注册按钮
	private Button btn_login; // 登陆按钮
	private EditText uid_edit, pass_edit;
	
	
	/**更所登陆项的菜单是否展开，默认收起*/
//	private boolean isShowMenu = false;
	
	private Handler mHandler;
	public static final int MENU_PWD_BACK = 1;
	public static final int MENU_HELP = 2;
	public static final int MENU_EXIT = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		initView();
	}
	
	private void initView(){
		
		btn_login_regist = (Button) findViewById(R.id.btn_login_regist);
		btn_login_regist.setOnClickListener(this);
		
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
		
		uid_edit = (EditText) findViewById(R.id.et_qqNum);
		pass_edit = (EditText) findViewById(R.id.et_qqPwd);

		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
					case Global.LOGIN_SUCCESS:
						Intent intents = new Intent(LoginActivity.this, HomeActivity.class);
						startActivity(intents);
						break;
					case Global.LOGIN_ERROR:
//						(Toast.makeText(this, "正确填写手机号", Toast.LENGTH_LONG)).show();
//						Toast.makeText(this, "账号密码错误", Toast.LENGTH_LONG).show();
						break;
				}
			}
		};
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.btn_login_regist:
			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_login:
			String uid = uid_edit.getText().toString();
			String pwd = pass_edit.getText().toString();
			Login(uid,pwd);
//			Toast.makeText(this, "正确填写手机号", Toast.LENGTH_LONG).show();
//			Intent intents = new Intent(LoginActivity.this, HomeActivity.class);
//			startActivity(intents);
			break;
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {//创建系统功能菜单
		// TODO Auto-generated method stub
		menu.add(0, MENU_PWD_BACK, 1, "密码找回").setIcon(R.drawable.menu_findkey);
		menu.add(0,MENU_HELP,2,"帮助").setIcon(R.drawable.menu_setting);
		menu.add(0, MENU_EXIT, 3, "退出").setIcon(R.drawable.menu_exit);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case MENU_PWD_BACK:
			break;
		case MENU_HELP:
			break;
		case MENU_EXIT:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void Login(String uid, String pass){
		PostThread postThread = new PostThread(uid, pass);
		postThread.start();
	}
	
	
	class PostThread extends Thread{
		String uid;
		String pwd;
		
		public PostThread(String uid, String pwd){
			this.uid = uid;
			this.pwd = pwd;
		}
		
		public void run(){
			HttpClient httpClient = new DefaultHttpClient();
			String url = Global.REQUEST_URL;
			HttpPost post = new HttpPost(url);
			
			NameValuePair uidPair = new BasicNameValuePair("uid", this.uid);
			NameValuePair passPair = new BasicNameValuePair("pass", this.pwd);
			
			ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
			postData.add(uidPair);
			postData.add(passPair);
			try{
				HttpEntity requestEntity = new UrlEncodedFormEntity(postData);
				post.setEntity(requestEntity);
				try{
					HttpResponse response = httpClient.execute(post);
					if(response.getStatusLine().getStatusCode() == 200){
						HttpEntity resEntity = response.getEntity();
						BufferedReader reader = new BufferedReader
								(new InputStreamReader(resEntity.getContent()));
						String result = reader.readLine();
						JSONObject resJson = new JSONObject(result);
						
						if(resJson.getInt("result") == Global.REQUEST_SUCCESS){
							mHandler.sendEmptyMessage(Global.LOGIN_SUCCESS);
							Log.d("zlei","success");
						}else{
							mHandler.sendEmptyMessage(Global.LOGIN_ERROR);
							Log.d("zlei","error");
						}
						Log.d("zlei",result);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}

