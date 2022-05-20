package com.example.coding_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {
    private ArrayList<ModelClass> user_list;


    public AdapterClass(ArrayList<ModelClass> user_list){
        this.user_list=user_list;
    }

    @NonNull
    @Override
    public AdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_template,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.ViewHolder holder, int position) {
        String name,town;
        int photo;
        name=user_list.get(position).getName();
        town=user_list.get(position).getTown();
        photo=user_list.get(position).getPhoto();

        holder.tv_name.setText(name);
        holder.tv_town.setText(town);
        holder.img_user.setImageResource(photo);



    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_town;
        private CircleImageView img_user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.textView99);
            tv_town=itemView.findViewById(R.id.tv_town);
            img_user=itemView.findViewById(R.id.circleImageView);

        }
    }
}
