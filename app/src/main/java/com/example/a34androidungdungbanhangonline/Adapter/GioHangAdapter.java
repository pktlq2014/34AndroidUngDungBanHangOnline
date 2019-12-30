package com.example.a34androidungdungbanhangonline.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;

import com.example.a34androidungdungbanhangonline.Activity.MainActivity;
import com.example.a34androidungdungbanhangonline.Model.GioHang;
import com.example.a34androidungdungbanhangonline.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter
{
    Context context;
    ArrayList<GioHang> arrayList;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount()
    {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public class viewHolder
    {
        public TextView textView1, textView2;
        public ImageView imageView1;
        public Button button1, button2, button3;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        viewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new viewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_giohang, null);
            viewHolder.textView1 = convertView.findViewById(R.id.textViewTenGioHang);
            viewHolder.textView2 = convertView.findViewById(R.id.textViewGiaGioHang);
            viewHolder.imageView1 = convertView.findViewById(R.id.imageViewGioHang);
       //     viewHolder.imageViewXoa = convertView.findViewById(R.id.imageViewXoa);
            viewHolder.button1 = convertView.findViewById(R.id.butTonMinus);
            viewHolder.button2 = convertView.findViewById(R.id.butTonValues);
            viewHolder.button3 = convertView.findViewById(R.id.butTonPlus);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (GioHangAdapter.viewHolder) convertView.getTag();
        }

        final GioHang gioHang = (GioHang) getItem(position);
        viewHolder.textView1.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textView2.setText(decimalFormat.format(gioHang.getGiasp()) + " Đ");
        Picasso.with(context).load(gioHang.getHinhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imageView1);
        viewHolder.button2.setText(gioHang.getSoluongsp() + "");

        // thiết lập button + và -
        int sl = Integer.parseInt(viewHolder.button2.getText().toString());
        if(sl >= 10)
        {
            viewHolder.button3.setVisibility(View.INVISIBLE);
            viewHolder.button1.setVisibility(View.VISIBLE);
        }
        else if(sl <= 1)
        {
            viewHolder.button1.setVisibility(View.INVISIBLE);
            viewHolder.button3.setVisibility(View.VISIBLE);
        }
        else if(sl >= 1)
        {
            viewHolder.button3.setVisibility(View.VISIBLE);
            viewHolder.button1.setVisibility(View.VISIBLE);
        }









        final viewHolder viewHolder1 = viewHolder;
        // xét sự kiện button +
        viewHolder.button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int soLuongMoiNhat = Integer.parseInt(viewHolder1.button2.getText().toString()) + 1;
                int soLuongHienTai = MainActivity.mangGioHang.get(position).getSoluongsp();
                long giaHienTai = MainActivity.mangGioHang.get(position).getGiasp();
                MainActivity.mangGioHang.get(position).setSoluongsp(soLuongMoiNhat);
                long giaMoiNhat = (giaHienTai * soLuongMoiNhat) / soLuongHienTai;
                MainActivity.mangGioHang.get(position).setGiasp(giaMoiNhat);
                DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
                viewHolder1.textView2.setText(decimalFormat1.format(giaMoiNhat) + " Đ");
                com.example.a34androidungdungbanhangonline.Activity.GioHang.eventUtil();
                if(soLuongMoiNhat > 9)
                {
                    viewHolder1.button3.setVisibility(View.INVISIBLE);
                    viewHolder1.button1.setVisibility(View.VISIBLE);
                    viewHolder1.button2.setText(String.valueOf(soLuongMoiNhat));
                }
                else
                {
                    viewHolder1.button3.setVisibility(View.VISIBLE);
                    viewHolder1.button1.setVisibility(View.VISIBLE);
                    viewHolder1.button2.setText(String.valueOf(soLuongMoiNhat));
                }
            }
        });
        // xét sự kiện button -
        viewHolder.button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int soLuongMoiNhat = Integer.parseInt(viewHolder1.button2.getText().toString()) - 1;
                int soLuongHienTai = MainActivity.mangGioHang.get(position).getSoluongsp();
                long giaHienTai = MainActivity.mangGioHang.get(position).getGiasp();
                MainActivity.mangGioHang.get(position).setSoluongsp(soLuongMoiNhat);
                long giaMoiNhat = (giaHienTai * soLuongMoiNhat) / soLuongHienTai;
                MainActivity.mangGioHang.get(position).setGiasp(giaMoiNhat);
                DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
                viewHolder1.textView2.setText(decimalFormat1.format(giaMoiNhat) + " Đ");
                com.example.a34androidungdungbanhangonline.Activity.GioHang.eventUtil();
                if(soLuongMoiNhat < 2)
                {
                    viewHolder1.button1.setVisibility(View.INVISIBLE);
                    viewHolder1.button3.setVisibility(View.VISIBLE);
                    viewHolder1.button2.setText(String.valueOf(soLuongMoiNhat));
                }
                else
                {
                    viewHolder1.button3.setVisibility(View.VISIBLE);
                    viewHolder1.button1.setVisibility(View.VISIBLE);
                    viewHolder1.button2.setText(String.valueOf(soLuongMoiNhat));
                }
            }
        });



        return convertView;
    }


}
