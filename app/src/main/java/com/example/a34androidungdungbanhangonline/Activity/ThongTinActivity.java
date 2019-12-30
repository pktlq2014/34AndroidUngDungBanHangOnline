package com.example.a34androidungdungbanhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a34androidungdungbanhangonline.R;
import com.example.a34androidungdungbanhangonline.Ultil.CheckConnection;
import com.example.a34androidungdungbanhangonline.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinActivity extends AppCompatActivity
{
    EditText editText1, editText2, editText3, editText4;
    Button button1, button2;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2;
    String bbb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);

        anhXa();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    switch (checkedId)
                    {
                        case R.id.radioTaiNha:
                        {
                            Toast.makeText(ThongTinActivity.this, "Bạn Chọn Hình Thức Thanh Toán Tại Nhà!!!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.id.radioTrucTuyen:
                        {
                            Toast.makeText(ThongTinActivity.this, "Hình Thức Thanh Toán Trực Tuyến Chưa Được Thiết Lập!. Xin Vui Lòng Chọn Lại Hình Thức Khác", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            });
            eventButton();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Kết Nối");
        }
    }

    private void eventButton() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = editText1.getText().toString().trim();
                final String soDienThoai = editText2.getText().toString().trim();
                final String email = editText3.getText().toString().trim();
                final String diaChi = editText4.getText().toString().trim();
                if(radioButton1.isChecked())
                {
                    bbb = radioButton1.getText().toString().trim();
                }
                else
                {
                //    Toast.makeText(ThongTinActivity.this, "Hình Thức Thanh Toán Trực Tuyến Chưa Được Thiết Lập!. Xin Vui Lòng Chọn Lại Hình Thức Khác", Toast.LENGTH_SHORT).show();
                    bbb = radioButton1.getText().toString().trim();
                }
                if (ten.length() > 0 && soDienThoai.length() > 0 && email.length() > 0 && diaChi.length() > 0 && bbb.length() > 0) {
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongDanDonHang, new Response.Listener<String>() {
                        @Override
                        // thay biến response thành madonhang cho dễ nhìn
                        public void onResponse(final String maDonHang) {
                            //
                            if (Integer.parseInt(maDonHang) > 0) {
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.duongDanChiTietDonHang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response1)
                                    {
                                        // or String a = response1.trim();
                                        // mã chi tiet đơn hàng
                                        if (response1.equals("1 "))
                                        {
                                            MainActivity.mangGioHang.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Mời Bạn Tiếp Tục Mua Hàng");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Đơn Hàng Của Bạn Đã Được Gửi Vào Hệ Thống!. Quản Lý Sẽ Liên Hệ Với Bạn Sớm Nhất");
                                        }
                                        else
                                        {
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Thêm Dữ Liệu Giỏ Hàng Thất Bại");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", maDonHang);
                                                jsonObject.put("masanpham", MainActivity.mangGioHang.get(i).getIdsp());
                                                jsonObject.put("tensanpham", MainActivity.mangGioHang.get(i).getTensp());
                                                jsonObject.put("giasanpham", MainActivity.mangGioHang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham", MainActivity.mangGioHang.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap1 = new HashMap<String, String>();
                                        hashMap1.put("json", jsonArray.toString());
                                        return hashMap1;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang", ten);
                            hashMap.put("sodienthoai", soDienThoai);
                            hashMap.put("email", email);
                            hashMap.put("diachi", diaChi);
                            hashMap.put("thanhtoan", bbb);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Dữ Liệu");
                }
            }
        });
    }

    private void anhXa() {
        editText1 = findViewById(R.id.editTextTenKhachHang);
        editText2 = findViewById(R.id.editTextSoDienThoai);
        editText3 = findViewById(R.id.editTextEmail);
        button1 = findViewById(R.id.butTonXacNhan);
        button2 = findViewById(R.id.butTonTrove);

        editText4 = findViewById(R.id.editTextDiaChi);
        radioGroup = findViewById(R.id.radioThanhToan);
        radioButton1 = findViewById(R.id.radioTaiNha);
        radioButton2 = findViewById(R.id.radioTrucTuyen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuGioHang: {
                Intent intent = new Intent(getApplicationContext(), com.example.a34androidungdungbanhangonline.Activity.GioHang.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
