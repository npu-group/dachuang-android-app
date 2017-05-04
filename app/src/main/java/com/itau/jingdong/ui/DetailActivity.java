package com.itau.jingdong.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itau.jingdong.R;
import com.itau.jingdong.itemList.ImageThread;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.utils.CommonTools;
import com.itau.jingdong.widgets.AutoClearEditText;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
public class DetailActivity extends BaseActivity {

    Handler handler;
    private TextView itemTitleTextView = null;
    private TextView itemInfoTextView = null;
    private ImageView imageView = null;
    private Button detailAddToCart = null;

    // 文字介绍
    private String itemId = null;
    private String itemTitle = null;
    private String itemInfo = null;
    private String url = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

//		//新页面接收数据
//		Bundle bundle = this.getIntent().getExtras();
//		//接收name值
//		String name = bundle.getString("name");
//		Log.i("获取到的name值为",name);

        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
        itemTitle = intent.getStringExtra("itemTitle");
        itemInfo = intent.getStringExtra("itemInfo");
        url = intent.getStringExtra("url");
        Log.i("获取到的name值为", itemId);

		findViewById();
		initView();

    }

	@Override
	protected void findViewById() {
        itemTitleTextView = (TextView) findViewById(R.id.itemTitle);
        itemInfoTextView = (TextView) findViewById(R.id.itemInfo);
        imageView = (ImageView) findViewById(R.id.imageView);
        detailAddToCart = (Button) findViewById(R.id.detailAddToCart);
	}

	@Override
	protected void initView() {
        // 设置图片
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.home_logo));

        // 设置介绍文字
        itemTitleTextView.setText("标题：" + itemTitle);
        itemInfoTextView.setText("详细介绍：" + itemInfo);
//        imageView.setImageBitmap(returnBitMap(url));
//        new ImageThread(url, handler, imageView).start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Bitmap bmp = returnBitMap(url);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = bmp;
                System.out.println("000");
                handle.sendMessage(msg);
            }
        }).start();
    }

    //在消息队列中实现对控件的更改
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    System.out.println("111");
                    Bitmap bmp=(Bitmap)msg.obj;
                    imageView.setImageBitmap(bmp);
                    break;
            }
        };
    };

    // 加入购物车 按钮点击
    //第三种方式(Android1.6版本及以后的版本中提供了)
    public void addToCart(View view){
        Toast toast=Toast.makeText(getApplicationContext(), "加入购物车成功！", Toast.LENGTH_SHORT);
        toast.show();

    }

    public Bitmap returnBitMap(String url){
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
