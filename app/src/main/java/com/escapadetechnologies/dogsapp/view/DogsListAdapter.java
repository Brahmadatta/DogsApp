package com.escapadetechnologies.dogsapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.escapadetechnologies.dogsapp.R;
import com.escapadetechnologies.dogsapp.model.DogBreed;

import java.util.ArrayList;
import java.util.List;

public class DogsListAdapter extends RecyclerView.Adapter<DogsListAdapter.DogViewHolder> {

    private ArrayList<DogBreed> mDogBreeds;

    public DogsListAdapter(ArrayList<DogBreed> dogBreeds) {
        this.mDogBreeds = dogBreeds;
    }

    public void updateDogsList(List<DogBreed> newDogsList){
        mDogBreeds.clear();
        mDogBreeds.addAll(newDogsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog,parent,false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {

        ImageView imageView = holder.itemView.findViewById(R.id.imageView);
        TextView name = holder.itemView.findViewById(R.id.dogName);
        TextView lifeSpan = holder.itemView.findViewById(R.id.dog_lifespan);

        name.setText(mDogBreeds.get(position).dogBreed);
        lifeSpan.setText(mDogBreeds.get(position).lifeSpan);
    }

    @Override
    public int getItemCount() {
        return mDogBreeds.size();
    }

    public class DogViewHolder extends RecyclerView.ViewHolder{

        public View itemView;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
