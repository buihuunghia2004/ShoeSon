package com.example.shoeson.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.shoeson.Model.Brand;
import com.example.shoeson.Model.Shoes;
import com.example.shoeson.R;
import com.example.shoeson.databinding.ItemBrandBinding;

import java.util.List;

public class GridBrandAdapter extends BaseAdapter {
    private List<Brand> listBrand;

    public GridBrandAdapter(List<Brand> listBrand) {
        this.listBrand = listBrand;
    }

    @Override
    public int getCount() {
        return listBrand.size();
    }

    @Override
    public Object getItem(int position) {
        return listBrand.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemBrandBinding binding=ItemBrandBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        if (convertView == null) {
            convertView=binding.getRoot();
        }

        Brand brand=listBrand.get(position);
        binding.imgLogo.setImageResource(R.drawable.logos1);
        binding.tvName.setText(brand.getName());
        return convertView;
    }
}
