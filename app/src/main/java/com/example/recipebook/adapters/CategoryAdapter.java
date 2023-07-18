package com.example.recipebook.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.CategoryIndexActivity;
import com.example.recipebook.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

	private Context context;
	private int user_id;
	private ArrayList<Integer> Categories;
	private ArrayList<String> Categories_title;
	private int index;

	class ViewHolder extends RecyclerView.ViewHolder {
		TextView category_title;
		ImageView category_image;
		LinearLayout linearLayout;

		ViewHolder(View itemView) {
			super(itemView);
			category_image = itemView.findViewById(R.id.category_image);
			category_title = itemView.findViewById(R.id.category_title);
			linearLayout = itemView.findViewById(R.id.ll_category);
		}
	}

	public CategoryAdapter(Context context, ArrayList<Integer> Categories, ArrayList<String> Categories_title, int user_id, int index){
		this.context = context;
		this.Categories = Categories;
		this.Categories_title = Categories_title;
		this.user_id = user_id;
		this.index = index;
	}

	@Override
	public int getItemCount() {
		return Categories.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_category, viewGroup, false);
		ViewHolder pvh = new ViewHolder(v);
		return pvh;
	}

	@Override
	public void onBindViewHolder(ViewHolder recipeViewHolder, final int position) {
		recipeViewHolder.category_title.setText(Categories_title.get(position));
		recipeViewHolder.category_image.setImageResource(Categories.get(position));

		recipeViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CategoryIndexActivity.class);
				intent.putExtra("user_id", user_id);
				intent.putExtra("category_id", position);
				context.startActivity(intent);
			}
		});
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}
}