package com.example.srimanth.recyclerviewfun;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by srimanth on 2/10/18.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder> {

    private String data1[],data2[];
    private int img[];
    private Context ctx;

    public CustomRecyclerViewAdapter(Context context,String s1[],String s2[], int i1[])
    {
        ctx = context;
        data1 = s1;
        data2 = s2;
        img = i1;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater myLayoutInflater = LayoutInflater.from(ctx);
        View customView = myLayoutInflater.inflate(R.layout.my_row_layout,parent,false);

        return new CustomViewHolder(customView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.t1.setText(data1[position]);
        holder.t2.setText(data2[position]);
        holder.myImageView.setImageResource(img[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView t1,t2;
        ImageView myImageView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.textView1);
            t2=itemView.findViewById(R.id.textView2);
            myImageView=itemView.findViewById(R.id.imageView);

        }
    }
}
