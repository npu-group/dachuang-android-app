package com.itau.jingdong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itau.jingdong.R;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.utils.CommonTools;
import com.itau.jingdong.widgets.AutoClearEditText;

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

    private TextView textView = null;
    private ImageView imageView = null;
    private Button detailAddToCart = null;

    // 文字介绍
    private String itemId = null;

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
        Log.i("获取到的name值为", itemId);

		findViewById();
		initView();

    }

	@Override
	protected void findViewById() {
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        detailAddToCart = (Button) findViewById(R.id.detailAddToCart);
	}

	@Override
	protected void initView() {
        // 设置图片
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.home_logo));

        // 设置介绍文字
        textView.setText(itemId);

    }

    // 加入购物车 按钮点击
    //第三种方式(Android1.6版本及以后的版本中提供了)
    public void addToCart(View view){
        Toast toast=Toast.makeText(getApplicationContext(), "加入购物车成功！", Toast.LENGTH_SHORT);
        toast.show();

    }
}
