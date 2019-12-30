package com.example.a34androidungdungbanhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a34androidungdungbanhangonline.Model.GioHang;
import com.example.a34androidungdungbanhangonline.Model.SanPham;
import com.example.a34androidungdungbanhangonline.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity
{
    Toolbar toolbar;
    ImageView imageView1;
    TextView textView1, textView2, textView3;
    Spinner spinner;
    Button butTon1;
    ScaleGestureDetector scaleGestureDetector;

    int idChiTiet = 0;
    String tenChiTiet = "";
    Integer giaChiTiet = 0;
    String hinhAnhChiTiet = "";
    String moTaChiTiet = "";
    int idSanPham = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhXa();
        actionToolBar();
        getInForMaTion();
        catchEventSpinner();
        eventButton();
    }

    private void eventButton()
    {
        butTon1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(MainActivity.mangGioHang.size() > 0)
                {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for(int i = 0; i < MainActivity.mangGioHang.size(); i++)
                    {
                        if(MainActivity.mangGioHang.get(i).getIdsp() == idChiTiet)
                        {
                            MainActivity.mangGioHang.get(i).setSoluongsp(MainActivity.mangGioHang.get(i).getSoluongsp() + sl);
                            if(MainActivity.mangGioHang.get(i).getSoluongsp() >= 10)
                            {
                                MainActivity.mangGioHang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangGioHang.get(i).setGiasp(giaChiTiet * MainActivity.mangGioHang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if(exists == false)
                    {
                        int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giaMoi = giaChiTiet * soLuong;
                        MainActivity.mangGioHang.add(new GioHang(idChiTiet, tenChiTiet, giaMoi, hinhAnhChiTiet, soLuong));
                    }
                }
                else
                {
                    int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giaMoi = giaChiTiet * soLuong;
                    MainActivity.mangGioHang.add(new GioHang(idChiTiet, tenChiTiet, giaMoi, hinhAnhChiTiet, soLuong));
                }
                Intent intent = new Intent(getApplicationContext(), com.example.a34androidungdungbanhangonline.Activity.GioHang.class);
                startActivity(intent);
            }
        });
    }

    private void catchEventSpinner()
    {
        Integer[] soLuong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(ChiTietSanPham.this, android.R.layout.simple_spinner_dropdown_item, soLuong);
        spinner.setAdapter(arrayAdapter);
    }

    private void getInForMaTion()
    {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        idChiTiet = sanPham.getId();
        tenChiTiet = sanPham.getTenSanPham();
        giaChiTiet = sanPham.getGiaSanPham();
        hinhAnhChiTiet = sanPham.getHinhAnhSanPham();
        moTaChiTiet = sanPham.getMoTaSanPham();
        idSanPham = sanPham.getIdSanPham();
        textView1.setText(tenChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textView2.setText("Giá : " + decimalFormat.format(giaChiTiet) + " Đ");
        textView3.setText(moTaChiTiet);
        Picasso.with(getApplicationContext()).load(hinhAnhChiTiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imageView1);
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
        toolbar = findViewById(R.id.toolBarChiTietSanPham);
        imageView1 = findViewById(R.id.imageViewChiTietSanPham);
        textView1 = findViewById(R.id.textViewTenChiTietSanPham);
        textView2 = findViewById(R.id.textViewGiaChiTietSanPham);
        textView3 = findViewById(R.id.textViewMoTaChiTietSanPham);
        spinner = findViewById(R.id.spinner);
        butTon1 = findViewById(R.id.butTonDatMua);

        // phóng to thu nhỏ ảnh
        scaleGestureDetector = new ScaleGestureDetector(ChiTietSanPham.this, new MyGesture());
        imageView1.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });
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



    class MyGesture extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        float scale = 1.0F, onScaleStart = 0, onScaleEnd = 0;
        @Override
        public boolean onScale(ScaleGestureDetector detector)
        {
            scale *= detector.getScaleFactor();
            imageView1.setScaleX(scale);
            imageView1.setScaleY(scale);
            return super.onScale(detector);
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector)
        {
            Toast.makeText(ChiTietSanPham.this, "onScaleStart", Toast.LENGTH_SHORT).show();
            onScaleStart = scale;
            return super.onScaleBegin(detector);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector)
        {
            Toast.makeText(ChiTietSanPham.this, "onScaleEnd", Toast.LENGTH_SHORT).show();
            onScaleEnd = scale;
            super.onScaleEnd(detector);
        }
    }
}
