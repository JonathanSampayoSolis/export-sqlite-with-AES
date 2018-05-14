package com.example.jjsampayo.cwac_saferoom_01;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jjsampayo.cwac_saferoom_01.data.entities.User;
import com.example.jjsampayo.cwac_saferoom_01.databinding.ItemMainActivityBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MainActivityViewHolder>{

    private List<User> data;

    public MainActivityAdapter() {
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public MainActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainActivityBinding binding = ItemMainActivityBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new MainActivityViewHolder(binding.getRoot());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityViewHolder holder, int position) {
        holder.binding.setUser(data.get(position));
    }

    public void setData(List<User> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    static class MainActivityViewHolder extends RecyclerView.ViewHolder {
        ItemMainActivityBinding binding;

        public MainActivityViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }
}
