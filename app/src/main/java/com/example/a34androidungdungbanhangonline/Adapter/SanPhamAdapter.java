package com.example.a34androidungdungbanhangonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a34androidungdungbanhangonline.Activity.ChiTietSanPham;
import com.example.a34androidungdungbanhangonline.Model.SanPham;
import com.example.a34androidungdungbanhangonline.R;
import com.example.a34androidungdungbanhangonline.Ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.itemHolder>
{
    Context context;
    ArrayList<SanPham> arrayList;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public itemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham, null);
        itemHolder itemHolder = new itemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemHolder holder, int position)
    {
        SanPham sanPham = arrayList.get(position);
        holder.textViewTenSanPham.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.textViewGiaSanPham.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");
        Picasso.with(context).load(sanPham.getHinhAnhSanPham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imageViewHinhSanPham);
    }


    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class itemHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageViewHinhSanPham;
        public TextView textViewTenSanPham, textViewGiaSanPham;

        public itemHolder(@NonNull View itemView)
        {
            super(itemView);
            imageViewHinhSanPham = itemView.findViewById(R.id.imageViewSanPham);
            textViewTenSanPham = itemView.findViewById(R.id.textViewTenSanPham);
            textViewGiaSanPham = itemView.findViewById(R.id.textViewGiaSanPham);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham", arrayList.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast_Short(context, arrayList.get(getPosition()).getTenSanPham());
                    context.startActivity(intent);
                }
            });
        }
    }
}
