package com.example.a34androidungdungbanhangonline.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a34androidungdungbanhangonline.Model.SanPham;
import com.example.a34androidungdungbanhangonline.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LapptopAdapter extends BaseAdapter
{
    Context context;
    ArrayList<SanPham> arrayList;

    public LapptopAdapter(Context context, ArrayList<SanPham> arrayList) {
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
        public TextView textView1, textView2, textView3;
        public ImageView imageView1;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        viewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new viewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_laptop, null);
            viewHolder.textView1 = convertView.findViewById(R.id.textViewLapTop);
            viewHolder.textView2 = convertView.findViewById(R.id.textViewGiaLapTop);
            viewHolder.textView3 = convertView.findViewById(R.id.textViewMoTaLapTop);
            viewHolder.imageView1 = convertView.findViewById(R.id.imageViewLapTop);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (LapptopAdapter.viewHolder) convertView.getTag();
        }
        SanPham sanPham = (SanPham) getItem(position);
        viewHolder.textView1.setText(sanPham.getTenSanPham());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textView2.setText("Giá : " + decimalFormat.format(sanPham.getGiaSanPham()) + " Đ");
        viewHolder.textView3.setMaxLines(2);
        viewHolder.textView3.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textView3.setText(sanPham.getMoTaSanPham());
        Picasso.with(context).load(sanPham.getHinhAnhSanPham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imageView1);

//        // gán animation
//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_list);
//        convertView.startAnimation(animation);
        return convertView;
    }
}
