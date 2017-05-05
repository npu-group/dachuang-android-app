package com.itau.jingdong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itau.jingdong.R;
import com.itau.jingdong.global.Global;
import com.itau.jingdong.global.SendMessage;
import com.itau.jingdong.ui.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

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

    private Handler handler;

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
        textView = (TextView) findViewById(R.id.itemInfo);
        imageView = (ImageView) findViewById(R.id.imageView);
        detailAddToCart = (Button) findViewById(R.id.detailAddToCart);
	}

	@Override
	protected void initView() {
        // 设置图片
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.home_logo));

        // 设置介绍文字
        textView.setText(itemId);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case Global.REQUEST_SUCCESS:
                        Log.i("zlei","Add TO Cart");
                        Toast toast=Toast.makeText(getApplicationContext(), "加入购物车成功！", Toast.LENGTH_SHORT);
                        toast.show();
                        break;
                    case Global.LOGIN_ERROR:
                        break;
                }
            }
        };

    }

    // 加入购物车 按钮点击
    //第三种方式(Android1.6版本及以后的版本中提供了)
    public void addToCart(View view){
        Log.i("zlei", "clickAddToCart");
        JSONObject paramJson = new JSONObject();
        try {
            paramJson.put("uid",Global.UID);
            paramJson.put("cid",itemId);
            paramJson.put("num", 1);

            Log.i("zlei","paramJson");
            Log.i("zlei",paramJson.toString());

//            AddToCartThread thread = new AddToCartThread(paramJson,Global.ADD_TO_CART);
//            thread.start();
            SendMessage sendMessage = new SendMessage(Global.ADD_TO_CART, paramJson, handler);
            sendMessage.start();
        } catch (JSONException e) {
//            e.printStackTrace();
            Toast toast=Toast.makeText(getApplicationContext(), "添加失败！", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

//    class AddToCartThread extends Thread{
//        JSONObject paramJson;
//        String url;
//
//
//        public AddToCartThread(JSONObject json,String url){
//            this.paramJson = json;
//        }
//
//        public void run(){
//            HttpClient httpClient = new DefaultHttpClient();
//            String url = Global.ADD_TO_CART;
//            HttpPost post = new HttpPost(url);
//
//            StringEntity entity = null;
//            try {
//                entity = new StringEntity(this.paramJson.toString(),"utf-8");
//                entity.setContentEncoding("UTF-8");
//                entity.setContentType("application/json");
//                post.setEntity(entity);
//
//                // 接收消息
//                HttpResponse result = httpClient.execute(post);
//                HttpEntity resEntity = result.getEntity();
//                BufferedReader reader = new BufferedReader
//                        (new InputStreamReader(resEntity.getContent()));
//
//                String resData = reader.readLine();
//                JSONObject resJson = new JSONObject(resData);
//
//                int feedback = resJson.getInt("feedback");
//                Message msg = Message.obtain();
//                msg.what = feedback;
//                msg.obj = resJson;
//                handler.sendMessage(msg);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (ClientProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }
}
