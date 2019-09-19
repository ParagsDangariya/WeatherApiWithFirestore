package com.example.weatherapiwithfirestore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Recyadapter extends RecyclerView.Adapter<Recyadapter.ViewHolder> {

    private ArrayList<Weather> weatherArray;
    private  Context c;
    private View.OnClickListener pokeListener;

    public Recyadapter(ArrayList<Weather> pokearray, Context c) {
        this.weatherArray = pokearray;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleitem,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(c).asBitmap().load(weatherArray.get(position).getImage()).into(holder.imgv);
        holder.txtv.setText(weatherArray.get(position).getName());

    }

    public void setOnClickListener(View.OnClickListener clickListener){

        pokeListener = clickListener;

    }

    @Override
    public int getItemCount() {
        return weatherArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        ImageView imgv;
        TextView txtv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgv = itemView.findViewById(R.id.imgv_poke);
            txtv = itemView.findViewById(R.id.txt_pkname);

            itemView.setTag(this);

            itemView.setOnClickListener(pokeListener);

        }
    }

}
