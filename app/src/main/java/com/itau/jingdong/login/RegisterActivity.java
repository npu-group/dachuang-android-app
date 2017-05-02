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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cogent.util.ClassPathResource;
import com.itau.jingdong.R;
import com.itau.jingdong.global.Global;
import com.itau.jingdong.ui.HomeActivity;
/**注册界面activity*/
public class RegisterActivity extends Activity implements android.view.View.OnClickListener{
	public static final int REGION_SELECT = 1;
	private TextView tv_QQ_Server,tv_region_modify,tv_region_show,tv_top_title;
	private Button btn_title_left,btn_title_right,btn_send_code;
	private CheckBox chk_agree;
	private EditText et_mobileNo, passEdit;
	private Handler mHandle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		initView();
	}
	
	private void initView(){
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_title.setText("账号注册");
		
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		btn_title_right.setVisibility(View.GONE);
		
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_left.setOnClickListener(this);
		
		btn_send_code = (Button) findViewById(R.id.btn_send_code);
		btn_send_code.setOnClickListener(this);
		
		tv_QQ_Server = (TextView) findViewById(R.id.tv_QQ_Server);
		tv_QQ_Server.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		tv_region_show = (TextView) findViewById(R.id.tv_region_show);
		
		tv_region_modify = (TextView) findViewById(R.id.tv_region_modify);
		tv_region_modify.setOnClickListener(this);
		
		chk_agree = (CheckBox) findViewById(R.id.chk_agree);
		et_mobileNo = (EditText) findViewById(R.id.et_mobileNo);
		passEdit = (EditText) findViewById(R.id.et_pass);
		mHandle = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
					case Global.REGISTER_SUCCESS:
						Intent intents = new Intent(RegisterActivity.this, LoginActivity.class);
						startActivity(intents);
						break;
					case Global.REGISTER_ERROR:
//						(Toast.makeText(this, "正确填写手机号", Toast.LENGTH_LONG)).show();
//						Toast.makeText(this, "账号密码错误", Toast.LENGTH_LONG).show();
						break;
				}
			}
		};
	}
	
	/**
	 * 重写了onCreateDialog方法来创建一个列表对话框
	 */
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		// TODO Auto-generated method stub
		switch(id){
		case REGION_SELECT:
			final Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("请选择所在地");
			builder.setSingleChoiceItems(//第一个参数是要显示的列表，用数组展示；第二个参数是默认选中的项的位置；
					//第三个参数是一个事件点击监听器
					new String[]{"+86中国大陆","+853中国澳门","+852中国香港","+886中国台湾"},
					0, 
					new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							switch(which){
							case 0:
								tv_region_show.setText("+86中国大陆");
								
								break;
							case 1:
								tv_region_show.setText("+853中国澳门");
								break;
							case 2:
								tv_region_show.setText("+852中国香港");
								break;
							case 3:
								tv_region_show.setText("+886中国台湾");
								break;
							}
							dismissDialog(REGION_SELECT);//想对话框关闭
						}
					});
			return builder.create();
		}
		return null;
	}

	private void register(String uid, String pass){
		RegisterRequest request = new RegisterRequest(uid, pass);
		request.start();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.tv_region_modify:
			showDialog(REGION_SELECT);//显示列表对话框的方法
			break;
		case R.id.btn_title_left:
			RegisterActivity.this.finish();
			break;
		case R.id.btn_send_code:
			String mobiles = et_mobileNo.getText().toString();
			if(chk_agree.isChecked()== false)//若没勾选checkbox无法后续操作
				Toast.makeText(this, "请确认是否已经阅读《服务条款》", Toast.LENGTH_LONG).show();
			if(ClassPathResource.isMobileNO(mobiles)==false)//对手机号码严格验证，参见工具类中的正则表达式
				Toast.makeText(this, "正确填写手机号", Toast.LENGTH_LONG).show();
			if(ClassPathResource.isMobileNO(mobiles)==true&&chk_agree.isChecked()==true){
				//当勾选中且号码正确，点击进行下一步操作
//				Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
				String uid = et_mobileNo.getText().toString();
				String pass = passEdit.getText().toString();
				register(uid,pass);
//				Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
//				startActivity(intent);
			}
		}
		
	}

	class RegisterRequest extends Thread{
		private String uid;
		private String pass;
		
		public RegisterRequest(String uid, String pass){
			this.uid = uid;
			this.pass = pass;
		}
		
		public void run(){
			// 创建HttpClient对象
			HttpClient httpClient = new DefaultHttpClient();
			// 发送的地址
			String url = Global.REGISTER_URL;
			// 创建Post对象
			HttpPost post = new HttpPost(url);
			
			// Post 需要传递的值
			NameValuePair uidPair = new BasicNameValuePair("uid", this.uid);
			NameValuePair passPair = new BasicNameValuePair("pass", this.pass);
			
			ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
			postData.add(uidPair);
			postData.add(passPair);
			try{
				// 返回消息
				HttpEntity requestEntity = new UrlEncodedFormEntity(postData);
				post.setEntity(requestEntity);
				try{
					HttpResponse response = httpClient.execute(post);
					// 如果请求正常
					if(response.getStatusLine().getStatusCode() == 200){
						HttpEntity resEntity = response.getEntity();
						BufferedReader reader = new BufferedReader
								(new InputStreamReader(resEntity.getContent()));
						String result = reader.readLine();
						JSONObject resJson = new JSONObject(result);
						
						if(resJson.getInt("result") == Global.REQUEST_SUCCESS){
							mHandle.sendEmptyMessage(Global.REGISTER_SUCCESS);
							Log.d("zlei","success");
						}else{
							mHandle.sendEmptyMessage(Global.LOGIN_ERROR);
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
