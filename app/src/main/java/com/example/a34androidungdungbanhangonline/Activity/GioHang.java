package com.example.a34androidungdungbanhangonline.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a34androidungdungbanhangonline.Adapter.GioHangAdapter;
import com.example.a34androidungdungbanhangonline.R;
import com.example.a34androidungdungbanhangonline.Ultil.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHang extends AppCompatActivity
{
    TextView textViewThongBaoGioHang;
    TextView textViewTongTien;
    static TextView textViewGiaTri;
    ListView listView1;
    Button buttonThanhToan, buttonTiepTucMuaHang;
    ArrayList<GioHang> arrayList;
    GioHangAdapter adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhXa();
        actionToolBar();
        checkData();
        eventUtil();
        catchEventDelete();
        eventButton();
        deleteProduct();



        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                xacNhanXoa(position);
            }
        });



        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác Nhận");
                builder.setMessage("Bạn Chắc Chắn Muốn Xóa");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (MainActivity.mangGioHang.size() <= 0)
                        {
                            textViewThongBaoGioHang.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            MainActivity.mangGioHang.remove(position);
                            adapter.notifyDataSetChanged();
                            // cập nhật lại tổng tiền
                            eventUtil();
                            if (MainActivity.mangGioHang.size() <= 0)
                            {
                                textViewThongBaoGioHang.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                textViewThongBaoGioHang.setVisibility(View.INVISIBLE);
                                adapter.notifyDataSetChanged();
                                eventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        adapter.notifyDataSetChanged();
                        eventUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public void deleteProduct()
    {

    }

    private void eventButton()
    {
        buttonThanhToan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(MainActivity.mangGioHang.size() > 0)
                {
              //      Intent intent = new Intent(getApplicationContext(), ThongTinActivity.class);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Giỏ Hàng Của Bạn Chưa Có Sản Phẩm Để Thanh Toán");
                }
            }
        });
        buttonTiepTucMuaHang.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void catchEventDelete()
    {
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác Nhận");
                builder.setMessage("Bạn Chắc Chắn Muốn Xóa");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (MainActivity.mangGioHang.size() <= 0)
                        {
                            textViewThongBaoGioHang.setVisibility(View.VISIBLE);
                        }
                        else
                            {
                            MainActivity.mangGioHang.remove(position);
                            adapter.notifyDataSetChanged();
                            // cập nhật lại tổng tiền
                            eventUtil();
                            if (MainActivity.mangGioHang.size() <= 0)
                            {
                                textViewThongBaoGioHang.setVisibility(View.VISIBLE);
                            }
                            else
                                {
                                textViewThongBaoGioHang.setVisibility(View.INVISIBLE);
                                adapter.notifyDataSetChanged();
                                eventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        adapter.notifyDataSetChanged();
                        eventUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void eventUtil()
    {
        long tongTien = 0;
        for(int i = 0; i < MainActivity.mangGioHang.size(); i++)
        {
            tongTien += MainActivity.mangGioHang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textViewGiaTri.setText(decimalFormat.format(tongTien) + " Đ");
    }

    private void checkData()
    {
        if(MainActivity.mangGioHang.size() <= 0)
        {
            adapter.notifyDataSetChanged();
            textViewThongBaoGioHang.setVisibility(View.VISIBLE);
            listView1.setVisibility(View.INVISIBLE);
        }
        else
        {
            adapter.notifyDataSetChanged();
            textViewThongBaoGioHang.setVisibility(View.INVISIBLE);
            listView1.setVisibility(View.VISIBLE);
        }
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
                finish();
            }
        });
    }

    private void anhXa()
    {
        textViewThongBaoGioHang = findViewById(R.id.textViewThongBao);
        textViewGiaTri = findViewById(R.id.textViewGiaTri);
        buttonTiepTucMuaHang = findViewById(R.id.butTonTiepTucMuaHang);
        buttonThanhToan = findViewById(R.id.butTonThanhToanGioHang);
        textViewTongTien = findViewById(R.id.textViewTongTien);
        listView1 = findViewById(R.id.listViewGioHang123);
        toolbar = findViewById(R.id.toolBarGioHang);
        arrayList = new ArrayList<>();
        adapter = new GioHangAdapter(GioHang.this, MainActivity.mangGioHang);
        listView1.setAdapter(adapter);
    }


    public void xacNhanXoa(final int position)
    {
        AlertDialog.Builder alertDiaLog = new AlertDialog.Builder(GioHang.this);
        alertDiaLog.setTitle("Thông Báo!");
        alertDiaLog.setIcon(R.mipmap.ic_launcher);
        alertDiaLog.setMessage("Bạn Chắc Chắn Muốn Xóa");
        alertDiaLog.setPositiveButton("Có", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (MainActivity.mangGioHang.size() <= 0)
                {
                    textViewThongBaoGioHang.setVisibility(View.VISIBLE);
                }
                else
                {
                    MainActivity.mangGioHang.remove(position);
                    adapter.notifyDataSetChanged();
                    // cập nhật lại tổng tiền
                    eventUtil();
                    if (MainActivity.mangGioHang.size() <= 0)
                    {
                        textViewThongBaoGioHang.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        textViewThongBaoGioHang.setVisibility(View.INVISIBLE);
                        adapter.notifyDataSetChanged();
                        eventUtil();
                    }
                }
            }
        });
        alertDiaLog.setNegativeButton("Không", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                adapter.notifyDataSetChanged();
                eventUtil();
            }
        });
        alertDiaLog.show();
    }
}
