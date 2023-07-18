package com.example.recipebook.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.R;
import com.example.recipebook.Recipe_Activity;
import com.example.recipebook.models.Recipe;

import java.util.List;

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.ViewHolder> {

	private Context context;
	private List<Recipe> recipes;
	private int user_id;
	private int index;

	class ViewHolder extends RecyclerView.ViewHolder {
		CardView cv;
		TextView recipe_title;
		TextView recipe_time;
		ImageView recipe_image;

		ViewHolder(View itemView) {
			super(itemView);
			cv = itemView.findViewById(R.id.recipe_CV);
			recipe_title = itemView.findViewById(R.id.recipe_title);
			recipe_image = itemView.findViewById(R.id.recipe_image);
			recipe_time = itemView.findViewById(R.id.model_recipe_cards_time);
		}
	}

	public RecipeCardAdapter(Context context, List<Recipe> recipes, int user_id, int index){
		this.context = context;
		this.recipes = recipes;
		this.user_id = user_id;
		this.index = index;
	}

	@Override
	public int getItemCount() {
		return recipes.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_recipe_cards, viewGroup, false);
		ViewHolder pvh = new ViewHolder(v);
		return pvh;
	}

	@Override
	public void onBindViewHolder(ViewHolder recipeViewHolder, final int position) {
		Recipe recipe = recipes.get(position);
		recipeViewHolder.recipe_time.setText(recipe.getTime());
		recipeViewHolder.recipe_title.setText(recipe.getRecipe_title());
		recipeViewHolder.recipe_image.setImageResource(recipe.getImage());

		recipeViewHolder.cv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context, Recipe_Activity.class);

				intent.putExtra("recipe_id",recipes.get(position).getId());
				intent.putExtra("recipe_title",recipes.get(position).getRecipe_title());
				intent.putExtra("time",recipes.get(position).getTime());
				intent.putExtra("Ingredients",recipes.get(position).getListIngredients());
				intent.putExtra("Kol",recipes.get(position).getListKol());
				intent.putExtra("Steps",recipes.get(position).getListSteps());
				intent.putExtra("image",recipes.get(position).getImage());
				intent.putExtra("user_id", user_id);
				intent.putExtra("index", index);

				context.startActivity(intent);
			}
		});
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}
}

