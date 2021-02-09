package com.example.assignment1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainRecyclerView extends RecyclerView.Adapter {

    private ArrayList<Cites> data;

    public MainRecyclerView (ArrayList<Cites> data){
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycle,parent,false);
        return new abcViewHolder(view);
    }

    public Cites getItem(int position){
        return data.get(position);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Cites tlt = getItem(position);
        ((abcViewHolder) holder).txtTitle.setText(tlt.getName());
        ((abcViewHolder) holder).textView.setText(tlt.getBehindOrAhead());
        ((abcViewHolder) holder).time.setText(tlt.getTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class abcViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgView;
        TextView txtTitle;
        TextView textView;
        TextView time;
        public abcViewHolder(@NonNull View itemView) {
            super(itemView);
            ImgView = (ImageView) itemView.findViewById(R.id.image);
            txtTitle = (TextView) itemView.findViewById(R.id.cityname);
            textView = (TextView) itemView.findViewById(R.id.textView);
            time = (TextView) itemView.findViewById(R.id.times);
        }
    }
}
