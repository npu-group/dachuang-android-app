package com.itau.jingdong.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.itau.jingdong.R;
import com.itau.jingdong.ui.base.BaseActivity;
import com.itau.jingdong.utils.CommonTools;
import com.itau.jingdong.widgets.AutoClearEditText;

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

	// private List<String> data = new ArrayList<String>();
    private ListView list = null;
    List<Map<String, Object>> listData = null; // 存放list的data
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_main);
        //绑定Layout里面的ListView
        list = (ListView) findViewById(R.id.ListView01);

		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.activity_item_list,
				new String[]{"itemTitle","itemInfo","itemImage"},
				new int[]{R.id.itemTitle,R.id.itemInfo,R.id.itemImage});
        list.setAdapter(adapter);

        //添加点击
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
                Intent intent =new Intent(ItemListActivity.this, DetailActivity.class);
                intent.putExtra("itemId", String.valueOf(listData.get(arg2).get("itemId")));

//                //用Bundle携带数据
//                Bundle bundle=new Bundle();
//                //传递name参数为tinyphp
//                bundle.putString("itemId", String.valueOf(listData.get(arg2).get("itemId")) );
//                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

	private List<Map<String, Object>> getData() {
		listData = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
        for(int i=0;i<10;i++)
        {
            map = new HashMap<String, Object>();
            map.put("itemImage", R.drawable.home_logo);//图像资源的ID
            map.put("itemTitle", "Level "+i);
            map.put("itemId", "id:"+i);
            map.put("itemInfo", "ItemText1 ItemText1 ItemText1 ItemText1 ItemText1 ItemText1 ItemText1 Finished in 1 Min 54 Secs, 70 Moves! ");
            listData.add(map);
        }


		return listData;
	}
}
