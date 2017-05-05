package com.itau.jingdong.ui;

import android.app.Activity;
import android.app.LauncherActivity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.itau.jingdong.R;
import com.itau.jingdong.global.Global;
import com.itau.jingdong.global.SendMessage;
import com.itau.jingdong.itemList.JsonAdapter;
import com.itau.jingdong.itemList.JsonThread;
import com.itau.jingdong.itemList.Student;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.utils.CommonTools;
import com.itau.jingdong.widgets.AutoClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ItemListActivity extends Activity {

    Context context=this;
    ListView listView;
    Handler handler;
    JsonAdapter jsonAdapter;
    List<Student> students;

	// private List<String> data = new ArrayList<String>();
//    private ListView list = null;
//    List<Map<String, Object>> listData = null; // 存放list的data
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_main);

        listView= (ListView) findViewById(R.id.ListView01);
//        handler=new Handler();//获得一个handler对象，为后面的各个线程提供处理UI的依据
//        Log.i("hello", listData);
//        new JsonThread(context, listView, url, handler).start();

        //添加点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // 获取当前点击的listItem
                ListView listView = (ListView) arg0;
                Student listData = (Student) listView.getItemAtPosition(arg2);

//                ListItem listitem = (ListItem) arg0.getItemAtPosition(arg2);
                //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
                Intent intent = new Intent(ItemListActivity.this, DetailActivity.class);
//                intent.putExtra("itemId", String.valueOf(listData.get(arg2).get("itemId")));
                intent.putExtra("itemId", listData.itemId);
                intent.putExtra("itemTitle", listData.itemTitle);
                intent.putExtra("itemInfo", listData.itemInfo);
                intent.putExtra("url", listData.url);

                startActivity(intent);
            }
        });



        handler = new Handler(){
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {
                    case Global.REQUEST_SUCCESS:
                        students = getStudents(msg.obj.toString());
                        jsonAdapter=new JsonAdapter(context,handler,students); //传递关键参数MainActivity上下文对象context，MainActivity主线程的handler对象,处理好的List<Student>对象
                        listView.setAdapter(jsonAdapter);
                        break;
                    case Global.LOGIN_ERROR:
                        break;
                }
            }
        };

        Intent intent = getIntent();
        String comType = intent.getStringExtra("comType");
        Log.i("获取到的comType值为", comType);
        JSONObject paramJson = new JSONObject();
        try {
            paramJson.put("comType", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SendMessage sendMessage = new SendMessage(Global.LIST_ITEM, paramJson, handler);
        sendMessage.start();
//        students=getStudents(result.toString());//调用解析方法
//        jsonAdapter=new JsonAdapter(context,handler,students); //传递关键参数MainActivity上下文对象context，MainActivity主线程的handler对象,处理好的List<Student>对象
//        listView.setAdapter(jsonAdapter);

    }

    //从String中解析所需数据，如name，age，url，将他们装入Student中，再将Student逐条加入List<Student>中
    private List<Student> getStudents(String data){
        List<Student> students=new ArrayList<Student>();
        try {
            JSONObject object=new JSONObject(data);
//            if(object.getInt("deedback") == Global.REQUEST_SUCCESS){
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
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  students;
    }

}
