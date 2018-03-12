package com.example.srimanth.cryptoconverter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srimanth on 2/11/18.
 */

    public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder> {

        private List<DataClass> dataClassList;
        private ArrayList<Integer> imageList;
        private Context context;

        public CustomRecyclerViewAdapter(Context context, List<DataClass> dataClassList, ArrayList<Integer>imageArrayList)
        {
            this.context=context;
            this.dataClassList=dataClassList;
            imageList=imageArrayList;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater myLayoutInfalter=LayoutInflater.from(context);
            View customView=myLayoutInfalter.inflate(R.layout.my_row_layout,parent,false);

            return new CustomViewHolder(customView);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {

            holder.rankTextView.setText(dataClassList.get(position).getRank());
            holder.nameTextView.setText(dataClassList.get(position).getNames());
            holder.currencyTextView.setText(dataClassList.get(position).getCurrency());
            holder.variationTextView.setText(dataClassList.get(position).getVariation());


            holder.imageView.setImageResource(imageList.get(position));


        }

        @Override
        public int getItemCount() {
            return dataClassList.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            TextView rankTextView,nameTextView,currencyTextView,variationTextView;
            ImageView imageView;


            CustomViewHolder(View itemView) {
                super(itemView);

                rankTextView=itemView.findViewById(R.id.rank);
                nameTextView=itemView.findViewById(R.id.name);
                currencyTextView=itemView.findViewById(R.id.currency);
                variationTextView=itemView.findViewById(R.id.variation);

                imageView=itemView.findViewById(R.id.imageView);
            }
        }
    }

