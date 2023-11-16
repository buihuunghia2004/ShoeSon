package com.example.shoeson.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.shoeson.Interfaces.ISetBrandAdapter;
import com.example.shoeson.Model.Brand;
import com.example.shoeson.databinding.ItemBrandBinding;

import java.util.List;

public class GridBrandAdapter extends BaseAdapter {
    ISetBrandAdapter iSetBrandAdapter;

    public void ISetBrandAdapterListener(ISetBrandAdapter iSetBrandAdapter) {
        this.iSetBrandAdapter = iSetBrandAdapter;
    }

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
        Glide.with(parent.getContext()).load(brand.getLinkLogo()).into(binding.imgLogo);
        binding.tvName.setText(brand.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSetBrandAdapter.setBrandApter(brand.getId());
            }
        });
        return convertView;
    }
}
