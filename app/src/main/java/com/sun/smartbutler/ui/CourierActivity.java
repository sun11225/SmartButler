package com.sun.smartbutler.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;
import com.sun.smartbutler.R;
import com.sun.smartbutler.adapter.CourierAdapter;
import com.sun.smartbutler.entity.CourierData;
import com.sun.smartbutler.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_number;
    private Button btn_get_courier;
    private ListView mListView;
    private List<CourierData> list=new ArrayList<>();
    private CourierData courierData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        btn_get_courier = (Button) findViewById(R.id.btn_get_courier);
        btn_get_courier.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.mListView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_courier:
                //获取输入框的内容 判断是否为空 请求数据 解析数据
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                    //联网请求数据
                    String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIER_KEY
                            + "&com=" + name + "&no=" + number;

                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            //解析json
                            parseJson(t);
                        }

                        @Override
                        public void onFailure(VolleyError error) {
                            Toast.makeText(CourierActivity.this, error + "", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(CourierActivity.this, "输入框不能为空！", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    //解析json数据
    private void parseJson(String data) {
        try {
            JSONObject jsonObject1=new JSONObject(data);
            JSONObject result = jsonObject1.getJSONObject("result");
            JSONArray jsonArray =result.getJSONArray("list");

           // JSONArray jsonArray=new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                courierData = new CourierData();
                courierData.setDateTime(jsonObject.getString("datetime"));
                courierData.setRemark(jsonObject.getString("remark"));
                courierData.setZone(jsonObject.getString("zone"));
                list.add(courierData);
            }
            //倒序
            Collections.reverse(list);
            //显示数据
            CourierAdapter adapter = new CourierAdapter(CourierActivity.this, list);
            mListView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
