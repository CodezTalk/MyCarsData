package com.mahmoud.mycarsdata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.mahmoud.mycarsdata.R;
import com.mahmoud.mycarsdata.model.CarData;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.DataObjectHolder> {
    private ArrayList<CarData.Datum> mDataSet;
    private Context context;


    public static class DataObjectHolder extends RecyclerView.ViewHolder {


        TextView textCarBrand;
        TextView textCarIsUsed;
        ImageView imageCar;


        public DataObjectHolder(View view) {
            super(view);

            this.textCarBrand = view.findViewById(R.id.textCarBrand);
            this.textCarIsUsed = view.findViewById(R.id.textCarIsUsed);
            this.imageCar = view.findViewById(R.id.imageCar);

        }

    }

    public CarAdapter(ArrayList<CarData.Datum> mDataSet) {
        this.mDataSet = mDataSet;
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        context = parent.getContext();

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

        final CarData.Datum carData = mDataSet.get(position);
        holder.textCarBrand.setText(carData.getBrand());


        Glide.with(context)
                .load(carData.getImageUrl())
                .fitCenter()
                .placeholder(R.drawable.car)
                .into(holder.imageCar);

        if ((carData.getIsUsed())) {
            holder.textCarIsUsed.setText("used");

        } else {
            holder.textCarIsUsed.setText("new");

        }


    }

    public void addItem(CarData.Datum dataObj) {
        mDataSet.add(dataObj);
        notifyDataSetChanged();
    }

    public void deleteItem(int index) {
        mDataSet.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


}

