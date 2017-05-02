package com.itau.jingdong.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.itau.jingdong.R;
import com.itau.jingdong.global.Global;
import com.itau.jingdong.global.WebAppInterface;
import com.itau.jingdong.ui.base.BaseActivity;

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
public class CartActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car);
		
		WebView web = (WebView)findViewById(R.id.Shopping_car);
		
		web.loadUrl(Global.SHOPPING_CAR_URL);
		
		web.setWebViewClient(new WebViewClient());  
	        //得到webview设置  
        WebSettings webSettings = web.getSettings();    
        //允许使用javascript  
        webSettings.setJavaScriptEnabled(true); 
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setBuiltInZoomControls(true);
        
        //将WebAppInterface于javascript绑定  
        web.addJavascriptInterface(new WebAppInterface(this), "Android");
	}

	
	
	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		WebView browser=(WebView)findViewById(R.id.Shopping_car);  
        if ((keyCode == KeyEvent.KEYCODE_BACK) && browser.canGoBack()) {  
            browser.goBack();  
            return true;  
        }  
		return super.onKeyDown(keyCode, event);  
	}

}
