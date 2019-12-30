package com.example.a34androidungdungbanhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a34androidungdungbanhangonline.Adapter.DienThoaiAdapter;
import com.example.a34androidungdungbanhangonline.Model.SanPham;
import com.example.a34androidungdungbanhangonline.R;
import com.example.a34androidungdungbanhangonline.Ultil.CheckConnection;
import com.example.a34androidungdungbanhangonline.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity
{
    Toolbar toolbar;
    ListView listView1;
    DienThoaiAdapter adapter;
    ArrayList<SanPham> arrayList;
    ActionBar actionBar;
    View footerView;

    int idDienThoai = 0;
    int page = 1;

    boolean isLoading = false;
    mHandler mHandler;

    boolean limitData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);

        anhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            getLoaiSanPham();
            actionToolBar();
            getData(page);
            getMoreData();
        }
        else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Kết Nối");
            finish();
        }
    }

    private void getMoreData()
    {
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", arrayList.get(position));
                startActivity(intent);
            }
        });
        listView1.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false)
                {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void actionToolBar()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // mở ra navigation
                finish();
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
    }

    private void getData(int Page)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanDienThoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                int idDienThoai = 0;
                String tenDienThoai = "";
                Integer giaDienThoai = 0;
                String hinhAnhDienThoai = "";
                String moTaDienThoai = "";
                int idDienThoai1 = 0;
                if(response != null && response.length() != 2)
                {
                   //listView1.addFooterView(footerView);
                  // listView1.setAdapter(adapter);
                    listView1.removeFooterView(footerView);
                    try
                    {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idDienThoai = jsonObject.getInt("id");
                            tenDienThoai = jsonObject.getString("tensp");
                            giaDienThoai = jsonObject.getInt("giasp");
                            hinhAnhDienThoai = jsonObject.getString("hinhanhsp");
                            moTaDienThoai = jsonObject.getString("motasp");
                            idDienThoai1 = jsonObject.getInt("idsanpham");
                            arrayList.add(new SanPham(idDienThoai, tenDienThoai, giaDienThoai, hinhAnhDienThoai, moTaDienThoai, idDienThoai1));
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    limitData = true;
                    listView1.addFooterView(footerView);
                    listView1.setAdapter(adapter);
                   // listView1.removeFooterView(footerView);
               //     CheckConnection.ShowToast_Short(getApplicationContext(), "Đã Hết Sản Phẩm");
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> param = new HashMap<>();
                param.put("idsanpham", String.valueOf(idDienThoai));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }



    private void getLoaiSanPham()
    {
        idDienThoai = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisanpham", idDienThoai + "");
    }

    private void anhXa()
    {
        toolbar = findViewById(R.id.toolbarDienThoai);
        listView1 = findViewById(R.id.listViewDienThoai);
        arrayList = new ArrayList<>();
        adapter = new DienThoaiAdapter(getApplicationContext(), arrayList);
        listView1.setAdapter(adapter);


        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = layoutInflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            switch (msg.what)
            {
                case 0:
                {
                    listView1.addFooterView(footerView);
                    break;
                }
                case 1:
                {
                    getData(++page);
                    isLoading = false;
                    break;
                }
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread
    {
        @Override
        public void run()
        {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuGioHang:
            {
                Intent intent = new Intent(getApplicationContext(), com.example.a34androidungdungbanhangonline.Activity.GioHang.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
