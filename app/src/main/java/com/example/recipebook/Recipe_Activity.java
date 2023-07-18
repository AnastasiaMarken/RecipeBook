package com.example.recipebook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.DBHelpers.RecipesDBHelper;
import com.example.recipebook.adapters.CommentaryAdapter;
import com.example.recipebook.adapters.IngredientsRecyclerViewAdapter;
import com.example.recipebook.DBHelpers.LoginDBHelper;
import com.example.recipebook.adapters.StepsRecyclerViewAdapter;
import com.example.recipebook.models.Commentary;

import java.util.ArrayList;

public class Recipe_Activity extends AppCompatActivity {

	public static Recipe_Activity recipeActivity;

	private ImageView recipe_image;
	private ImageView heart;
	private ImageView previous_img_recipe;
	private ImageView home_img_recipe;
	private TextView recipe_title;
	private TextView recipe_time;
	private RecyclerView ingredients;
	private RecyclerView commentary_rv;
	private RecyclerView steps;
	private EditText et_comment;
	private RelativeLayout rl_comment;
	private Button cancel_comment;
	private Button add_comment;

	private boolean belongs;
	private ArrayList<String> Liked;
	private ArrayList<Commentary> Commentaries;
	private int index;
	private int recipe_id;
	private int user_id;

	private IngredientsRecyclerViewAdapter ingredientsRecyclerViewAdapter;
	private StepsRecyclerViewAdapter stepsRecyclerViewAdapter;
	private CommentaryAdapter commentaryAdapter;

	private LoginDBHelper DB;
	private RecipesDBHelper recipesDBHelper;
	private SQLiteDatabase usersDB;
	private SQLiteDatabase recipesDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recipe_activity);

		recipeActivity = this;

		Intent intent = getIntent();
		index = intent.getExtras().getInt("index");
		recipe_id = intent.getExtras().getInt("recipe_id");
		String title = intent.getExtras().getString("recipe_title");
		String time = intent.getExtras().getString("time");
		ArrayList<String> Ingredients = intent.getExtras().getStringArrayList("Ingredients");
		ArrayList<String> Kol = intent.getExtras().getStringArrayList("Kol");
		ArrayList<String> Steps = intent.getExtras().getStringArrayList("Steps");
		int image = intent.getExtras().getInt("image");
		int category_id = intent.getExtras().getInt("category_id") + 1;
		String category_title = intent.getExtras().getString("category_title");

		user_id = intent.getExtras().getInt("user_id");

		DB = new LoginDBHelper(this);
		usersDB = DB.getWritableDatabase();
		recipesDBHelper = new RecipesDBHelper(this);
		recipesDB = recipesDBHelper.getWritableDatabase();

		Commentaries = recipesDBHelper.getCommentary(recipe_id);
		Liked = DB.getLiked(user_id);
		belongs = false;

		for(int i = 0; i < Liked.size(); i++){
			if (Liked.get(i).equals(String.valueOf(recipe_id))){
				belongs = true;
			}
		}

		recipe_image = findViewById(R.id.recipeActivity_image);
		previous_img_recipe = findViewById(R.id.previous_img_recipe);
		home_img_recipe = findViewById(R.id.home_img_recipe);
		recipe_title = findViewById(R.id.recipeActivity_title);
		recipe_time = findViewById(R.id.recipeActivity_time);
		ingredients = findViewById(R.id.ingr_RV);
		steps = findViewById(R.id.steps_RV);
		heart = findViewById(R.id.heart);
		et_comment = findViewById(R.id.et_comment);
		rl_comment = findViewById(R.id.rl_comment);
		commentary_rv = findViewById(R.id.commentary);

		recipe_image.setImageResource(image);
		recipe_title.setText(title);
		recipe_time.setText(time);

		ingredientsRecyclerViewAdapter = new IngredientsRecyclerViewAdapter(this, Ingredients, Kol);
		ingredients.setLayoutManager(new LinearLayoutManager(this));
		ingredients.setAdapter(ingredientsRecyclerViewAdapter);

		stepsRecyclerViewAdapter = new StepsRecyclerViewAdapter(this, Steps);
		steps.setLayoutManager(new LinearLayoutManager(this));
		steps.setAdapter(stepsRecyclerViewAdapter);

		commentaryAdapter = new CommentaryAdapter(this, Commentaries);
		commentary_rv.setLayoutManager(new LinearLayoutManager(this));
		commentary_rv.setAdapter(commentaryAdapter);

		if(Liked.size() == 0 || belongs == false){
			heart.setImageResource(R.drawable.heart_1);
		} else if(belongs) heart.setImageResource(R.drawable.heart_2);


		onHeartClick();
		onHomeClick();
		onPreviousClick();
		addComment();
	}



	private void addComment(){
		final Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);

		LayoutInflater inflanter = getLayoutInflater();
		View toast_l = inflanter.inflate(R.layout.model_toast,  (ViewGroup) findViewById(R.id.toast_layout));
		toast.setView(toast_l);
		final TextView textToast = toast_l.findViewById(R.id.toast_1);

		et_comment.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				et_comment.setEnabled(true);
				rl_comment.setVisibility(View.VISIBLE);
				cancel_comment = findViewById(R.id.cancel_comment);
				add_comment = findViewById(R.id.add_comment);

				add_comment.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(et_comment.getText().length() == 0){
							textToast.setText("Оставьте комментарий");
							toast.show();
						} else {
							String text_comment = et_comment.getText().toString();
							Boolean insert = null;
							insert = recipesDBHelper.insertCommentary(user_id, recipe_id, text_comment);
							finish();
							overridePendingTransition(0, 0);
							startActivity(getIntent());
							overridePendingTransition(0, 0);
							et_comment.setText(null);
							et_comment.setEnabled(false);
							rl_comment.setVisibility(View.GONE);

						}
					}
				});

				cancel_comment.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View view) {
						et_comment.setText(null);
						et_comment.setEnabled(false);
						rl_comment.setVisibility(View.GONE);
					}
				});
			}
		});
	}

	private void onHeartClick() {
		heart.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Добавить в избранное
				if(belongs == false){
					String liked = "";

					Liked.add(String.valueOf(recipe_id));
					for(int i = 0; i < Liked.size(); i++){
						liked = liked + Liked.get(i) + "</n>";
					}

					DB.updateLiked(user_id, liked);
					heart.setImageResource(R.drawable.heart_2);
					belongs = true;
				}
				//Удалить из избранного
				else if(belongs) {
					String liked = "";

					int ix =  -1;
					for(int i = 0; i < Liked.size(); i++){
						if (Liked.get(i).equals(String.valueOf(recipe_id))){
							ix = i;
						}
					}
					Liked.remove(ix);
					for(int i = 0; i < Liked.size(); i++){
						liked = liked + Liked.get(i) + "</n>";
					}

					DB.updateLiked(user_id, liked);

					heart.setImageResource(R.drawable.heart_1);
					belongs = false;
				}
			}
		});
	}

	private void onHomeClick() {
		home_img_recipe.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(recipeActivity, HomeActivity.class);
				intent.putExtra("user_id", user_id);
				startActivity(intent);
				recipeActivity.finish();
			}
		});
	}
	private void onPreviousClick() {
		previous_img_recipe.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View view) {
				switch (index) {
					case 1:
						Intent intent = new Intent(recipeActivity, HomeActivity.class);
						intent.putExtra("user_id", user_id);
						startActivity(intent);
						recipeActivity.finish();
						break;

					case 2:
						Intent intent2 = new Intent(recipeActivity, LikedActivity.class);
						intent2.putExtra("user_id", user_id);
						startActivity(intent2);
						recipeActivity.finish();
						break;

					default:
						Intent intent3 = new Intent(recipeActivity, CategoryIndexActivity.class);
						intent3.putExtra("user_id", user_id);
						intent3.putExtra("category_id", index-10);
						startActivity(intent3);
						recipeActivity.finish();
						break;
				}
			}
		});
	}
	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		switch (index) {
			case 1:
				Intent intent = new Intent(recipeActivity, HomeActivity.class);
				intent.putExtra("user_id", user_id);
				startActivity(intent);
				recipeActivity.finish();
				break;

			case 2:
				Intent intent2 = new Intent(recipeActivity, LikedActivity.class);
				intent2.putExtra("user_id", user_id);
				startActivity(intent2);
				recipeActivity.finish();
				break;
			default:
				Intent intent3 = new Intent(recipeActivity, CategoryIndexActivity.class);
				intent3.putExtra("user_id", user_id);
				intent3.putExtra("category_id", index-10);
				startActivity(intent3);
				recipeActivity.finish();
				break;
		}
	}
}
