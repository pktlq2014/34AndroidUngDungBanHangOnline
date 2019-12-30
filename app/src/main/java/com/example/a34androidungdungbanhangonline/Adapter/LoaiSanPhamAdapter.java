package com.example.a34androidungdungbanhangonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a34androidungdungbanhangonline.Model.LoaiSanPham;
import com.example.a34androidungdungbanhangonline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSanPhamAdapter extends BaseAdapter
{
    Context context;
    ArrayList<LoaiSanPham> arrayList;


    public LoaiSanPhamAdapter(Context context, ArrayList<LoaiSanPham> arrayList) {
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
        TextView textView1;
        ImageView imageView1;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        viewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new viewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_loaisanpham, null);
            viewHolder.textView1 = convertView.findViewById(R.id.textViewLoaiSanPham);
            viewHolder.imageView1 = convertView.findViewById(R.id.imageViewLoaiSanPham);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (LoaiSanPhamAdapter.viewHolder) convertView.getTag();
        }
        LoaiSanPham loaiSanPham = (LoaiSanPham) getItem(position);
        viewHolder.textView1.setText(loaiSanPham.getTenLoaiSanPham());
        Picasso.with(context).load(loaiSanPham.getHinhAnhLoaiSanPham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imageView1);
        return convertView;
    }
}
