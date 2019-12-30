package com.example.a34androidungdungbanhangonline.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.a34androidungdungbanhangonline.Adapter.LoaiSanPhamAdapter;
import com.example.a34androidungdungbanhangonline.Adapter.SanPhamAdapter;
import com.example.a34androidungdungbanhangonline.FragmentCamera;
import com.example.a34androidungdungbanhangonline.Model.GioHang;
import com.example.a34androidungdungbanhangonline.Model.LoaiSanPham;
import com.example.a34androidungdungbanhangonline.Model.SanPham;
import com.example.a34androidungdungbanhangonline.R;
import com.example.a34androidungdungbanhangonline.Ultil.CheckConnection;
import com.example.a34androidungdungbanhangonline.Ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    ViewFlipper viewFlipper;
    Animation in,out;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    ListView listView1;
    ActionBar actionBar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    ImageView imageViewVietNam, imageViewHoaKy;




    ArrayList<LoaiSanPham> arrayList;
    LoaiSanPhamAdapter adapter;
    ArrayList<SanPham> arrayListSanPham;
    SanPhamAdapter adapterSanPham;



    int id = 0;
    String tenLoaiSP = "";
    String hinhAnhLoaiSP = "";


    public static ArrayList<GioHang> mangGioHang;
  //  int[] arrayHinh = {R.mipmap.ss1, R.mipmap.ss2, R.mipmap.ss3, R.mipmap.ss4, R.mipmap.god, R.mipmap.blue, R.mipmap.ultra};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            actionBar1();
            viewFlipper1();
         //   getDuLieuLoaiSanPham();
            getDuLieuSanPham();
            catchOnItem();
            ngonNgu();
        }
        else
        {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Kết Nối");
            finish();
        }


        // viewFlipper
//        for(int i = 0; i < arrayHinh.length; i++)
//        {
//            ImageView imageView = new ImageView(MainActivity.this);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setImageResource(arrayHinh[i]);
//            viewFlipper.addView(imageView);
//        }
//        in = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
//        out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
//        viewFlipper.setInAnimation(in);
//        viewFlipper.setInAnimation(out);
//        viewFlipper.setFlipInterval(3000);
//        viewFlipper.setAutoStart(true);



        // dùng để hiện nút 3 gạch nhưng cần actionBar(true) ở trên để kích hoạt
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
//        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();

    }

    private void ngonNgu()
    {
        imageViewVietNam.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chuyenNgonNgu("vi");
            }
        });
        imageViewHoaKy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chuyenNgonNgu("en");
            }
        });
    }

    private void catchOnItem()
    {
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 1:
                    {
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham", arrayList.get(position).getId());
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Kết Nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case 2:
                    {
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idloaisanpham", arrayList.get(position).getId());
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Kết Nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }

                    case 0:
                    {
                        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                        {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                        }
                        else
                        {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Kết Nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                }
            }
        });
    }

    private void getDuLieuSanPham()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanSanPham, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                if(response != null)
                {
                    int idSanPham = 0;
                    String tenSanPham = "";
                    Integer giaSanPham = 0;
                    String hinhAnhSanPham = "";
                    String moTaSanPham = "";
                    int id11 = 0;
                    for(int i = 0; i < response.length(); i++)
                    {
                        try
                        {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idSanPham = jsonObject.getInt("id");
                            tenSanPham = jsonObject.getString("tensp");
                            giaSanPham = jsonObject.getInt("giasp");
                            hinhAnhSanPham = jsonObject.getString("hinhanhsp");
                            moTaSanPham = jsonObject.getString("motasp");
                            id11 = jsonObject.getInt("idsanpham");
                            arrayListSanPham.add(new SanPham(idSanPham, tenSanPham, giaSanPham, hinhAnhSanPham, moTaSanPham, id11));
                            adapterSanPham.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
//                    arrayList.add(3, new LoaiSanPham(0, "Liên Hệ", "http://alobietthu.com/wp-content/uploads/2017/07/icon-contact-success.png"));
//                    arrayList.add(4, new LoaiSanPham(0, "Thông Tin", "https://cdn.pixabay.com/photo/2017/01/10/03/54/icon-1968239_960_720.png"));
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    // dùng Volley để dọc dữ liệu json
    private void getDuLieuLoaiSanPham()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanLoaiSanPham, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                if(response != null)
                {
                    for(int i = 0; i < response.length(); i++)
                    {
                        try
                        {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenLoaiSP = jsonObject.getString("tenloaisp");
                            hinhAnhLoaiSP = jsonObject.getString("hinhanhloaisp");
                            arrayList.add(new LoaiSanPham(id, tenLoaiSP, hinhAnhLoaiSP));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrayList.add(3, new LoaiSanPham(0, "Liên Hệ", "http://alobietthu.com/wp-content/uploads/2017/07/icon-contact-success.png"));
                    arrayList.add(4, new LoaiSanPham(0, "Thông Tin", "https://cdn.pixabay.com/photo/2017/01/10/03/54/icon-1968239_960_720.png"));
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void viewFlipper1()
    {
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("http://tinhte.cdnforo.com/store/2014/08/2572609_Hinh_2.jpg");
        arrayList1.add("http://cms.kienthuc.net.vn/zoom/1000/uploaded/nguyenvan/2016_12_07/ho/ngam-ho-ngoc-ha-cuc-quyen-ru-khi-quang-cao-dien-thoai-hinh-3.jpg");
        arrayList1.add("http://cdn.mediamart.vn/News/mua-vaio-rinh-qua-xperia-cung-media-mart-924201273231am.jpg");
        arrayList1.add("http://www.thongtincongnghe.com/sites/default/files/images/2012/1/13/img-1326420488-2.jpg");
        arrayList1.add("https://cdn.tgdd.vn/2019/12/banner/A51-800-300-800x300-1.png");
        arrayList1.add("https://cdn.tgdd.vn/2019/12/banner/A51-800-300-800x300-4.png");
        arrayList1.add("https://cdn.tgdd.vn/2019/12/banner/800-300-800x300-13.png");
        for(int i = 0; i < arrayList1.size(); i++)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrayList1.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setImageResource(arrayHinh[i]);
//            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);
    }




    private void actionBar1()
    {
        setSupportActionBar(toolbar);
        // kích hoạt hiện nút 3 gạch
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // setting icon đổi hình 3 gạch thành android =))
        // android.R.drawable.ic_menu_sort_by_size
   //     toolbar.setNavigationIcon(R.drawable.baseline_android_black_18dp);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        /// thiết lập chức năng mở navigation cho nút 3 dấu gạch ngang
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // mở ra navigation
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    // thiết lập chức năng khi click menu

    // thiết lập chức năng khi click menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.dienThoai:
            {
                if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                {
                    Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                //    intent.putExtra("idloaisanpham", arrayList.get(1).getId());
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                }
                else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Kết Nối");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.lapTop:
            {
                if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                {
                    Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                }
                else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Kết Nối");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.trangChu:
            {
                if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                }
                else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Kết Nối");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.lienHe:
            {
                if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                {
                    Toast.makeText(MainActivity.this, "Chưa Thiết Lập Chức Năng!!!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                }
                else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Kết Nối");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.thongTin:
            {
                if(CheckConnection.haveNetworkConnection(getApplicationContext()))
                {
                    Toast.makeText(MainActivity.this, "Chưa Thiết Lập Chức Năng!!!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                }
                else
                {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Xin Vui Lòng Kiểm Tra Lại Kết Nối");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
        }
        return false;
    }

    private void anhXa()
    {
        toolbar = findViewById(R.id.toolBar);
        viewFlipper = findViewById(R.id.viewFlipper);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.recyclerview);
        listView1 = findViewById(R.id.listViewManHinhChinh);
        imageViewVietNam = findViewById(R.id.imageViewVietNam);
        imageViewHoaKy = findViewById(R.id.imageViewHoaKy);

        arrayList = new ArrayList<>();
      //  arrayList.add(0, new LoaiSanPham(0, "Trang Chủ", "http://icons.iconarchive.com/icons/graphicloads/colorful-long-shadow/256/Home-icon.png"));

        adapter = new LoaiSanPhamAdapter(getApplicationContext(), arrayList);
        listView1.setAdapter(adapter);

        arrayListSanPham = new ArrayList<>();
        adapterSanPham = new SanPhamAdapter(getApplicationContext(), arrayListSanPham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(adapterSanPham);

        if(mangGioHang != null)
        {

        }
        else
        {
            mangGioHang = new ArrayList<>();
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


    public void chuyenNgonNgu(String ngonNgu)
    {
        Locale locale = new Locale(ngonNgu);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(
                configuration,
                getBaseContext().getResources().getDisplayMetrics()
        );
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
