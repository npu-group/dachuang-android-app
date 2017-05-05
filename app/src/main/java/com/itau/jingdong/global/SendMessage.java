package com.itau.jingdong.global;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ZLei on 2017/5/4.
 */

public class SendMessage extends Thread {
    private String url; // 发送消息的地址
    private JSONObject paramJson; // 发送消息传递的数据，Json形式格式
    private Handler handler; // 消息返回的处理

    public SendMessage(String url, JSONObject json, Handler handler){
        this.url = url;
        this.paramJson = json;
        this.handler = handler;
    }

    public void run(){

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(this.url);

        try {
            List<NameValuePair> postData = new ArrayList<NameValuePair>();
            Iterator<String> jsonItor = this.paramJson.keys();
            while(jsonItor.hasNext()){
                String key = jsonItor.next();
                String value = this.paramJson.getString(key);
                NameValuePair nameValuePair = new BasicNameValuePair(key,value);
                postData.add(nameValuePair);
            }

            HttpEntity entity = new UrlEncodedFormEntity(postData);
            post.setEntity(entity);

            // 接收消息
            HttpResponse result = httpClient.execute(post);

            HttpEntity resEntity = result.getEntity();
            BufferedReader reader = new BufferedReader(new InputStreamReader(resEntity.getContent()));

            String resData = reader.readLine();
            Log.i("zlei","resData : " + resData);

            JSONObject resJson = new JSONObject(resData);

            int feedback = resJson.getInt("feedback");
            Message msg = Message.obtain();
            msg.what = feedback;
            msg.obj = resJson;
            this.handler.sendMessage(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
