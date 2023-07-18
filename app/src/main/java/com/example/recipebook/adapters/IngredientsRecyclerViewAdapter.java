package com.example.recipebook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.R;

import java.util.ArrayList;

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientsViewHolder>{

	private ArrayList<String> Ingredients;
	private ArrayList<String> Kol;
	private Context context;

	public class IngredientsViewHolder extends RecyclerView.ViewHolder{

		TextView ingr;
		TextView kol;

		public IngredientsViewHolder(@NonNull View itemView) {
			super(itemView);

			ingr = itemView.findViewById(R.id.model_ListIngr);
			kol = itemView.findViewById(R.id.model_ListKol);

		}
	}

	public IngredientsRecyclerViewAdapter(Context context, ArrayList<String> Ingredients, ArrayList<String> Kol){
		this.context = context;
		this.Ingredients = Ingredients;
		this.Kol = Kol;
	}

	@Override
	public int getItemCount() {
		return Ingredients.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@NonNull
	@Override
	public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_ingredients, parent, false);
		IngredientsViewHolder ivh = new IngredientsViewHolder(view);
		return ivh;
	}

	@Override
	public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
		holder.ingr.setText(Ingredients.get(position));
		holder.kol.setText(Kol.get(position));
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}
}
