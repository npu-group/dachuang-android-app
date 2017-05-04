package com.itau.jingdong.itemList;

import android.content.Context;
import android.os.Handler;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CAPTON on 2016/11/25.
 */
//访问目标网址，得到json数据，保存List<Student>数据，等待传入JsonAdapter
public class JsonThread extends Thread {
    Context context;
    ListView listView;
    String url;
    Handler handler;//关键参数 整个小项目中的核心之一，会在JsonThread和JsonAdapter，ImageThread中传递，用于更新UI界面
    List<Student> students;
    JsonAdapter jsonAdapter;

    public JsonThread(Context context, ListView listView, String url, Handler handler ) {
        this.context=context;
        this.listView=listView;
        this.url=url;
        this.handler=handler;
    }
    //从String中解析所需数据，如name，age，url，将他们装入Student中，再将Student逐条加入List<Student>中
    private List<Student> getStudents(String data){
        List<Student> students=new ArrayList<Student>();
        try {
            JSONObject object=new JSONObject(data);
            if(object.getInt("data")==1){
                JSONArray jsonArray=object.getJSONArray("array");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject studentObject= (JSONObject) jsonArray.get(i);
                    Student student=new Student();
                    student.itemTitle=studentObject.getString("itemTitle");
                    student.itemId=studentObject.getString("itemId");
                    System.out.println(student.itemTitle);
                    student.itemInfo=studentObject.getString("itemInfo");
                    System.out.println(student.itemInfo);
                    student.url=studentObject.getString("url");
                    System.out.println(student.url);
                    students.add(student);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  students;
    }

    @Override
    public void run() {

        //从网络中获取数据，转换为String类型
        StringBuffer result=new StringBuffer();
        try {
            URL Url=new URL(url);
            HttpURLConnection connection= (HttpURLConnection) Url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            InputStream inputStream=connection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                result.append(line);
            }
            System.out.println(result);
            students=getStudents(result.toString());//调用解析方法
            inputStream.close();
            bufferedReader.close();

            handler.post(new Runnable() {
                @Override
                public void run() {

                    jsonAdapter=new JsonAdapter(context,handler,students); //传递关键参数MainActivity上下文对象context，MainActivity主线程的handler对象,处理好的List<Student>对象
                    listView.setAdapter(jsonAdapter);

                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }
}