package com.example.recipebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipebook.DBHelpers.LoginDBHelper;
import com.example.recipebook.DBHelpers.RecipesDBHelper;
import com.example.recipebook.adapters.RecipeCardAdapter;
import com.example.recipebook.models.Recipe;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

public class LikedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	public static LikedActivity likedActivity;

	private RecipesDBHelper recipesDBHelper;
	private LoginDBHelper loginDBHelper;

	private RecipeCardAdapter recipeCardAdapter;

	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	private List<Recipe> Recipes;
	private TextView menu_username;
	private ImageView menu_image;
	private int user_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liked_activity);

		likedActivity = this;

		Intent intent = getIntent();
		user_id = intent.getExtras().getInt("user_id");

		recipesDBHelper = new RecipesDBHelper(this);
		try {
			recipesDBHelper.updateDataBase();
		} catch (IOException mIOException) {
			throw new Error("UnableToUpdateDatabase");
		}

		Recipes = recipesDBHelper.getLikedRecipes(user_id);
		if(Recipes.size() == 0){
			final Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(Toast.LENGTH_LONG);
			LayoutInflater inflanter = getLayoutInflater();
			View toast_l = inflanter.inflate(R.layout.model_toast,  (ViewGroup) findViewById(R.id.toast_layout));
			toast.setView(toast_l);
			TextView textToast = toast_l.findViewById(R.id.toast_1);
			textToast.setText("У вас еще нет понравившихся рецептов");
			toast.show();
		}else {
			RecyclerView liked_RV = (RecyclerView) findViewById(R.id.liked_RV);
			recipeCardAdapter = new RecipeCardAdapter(this, Recipes, user_id, 2);
			liked_RV.setLayoutManager(new GridLayoutManager(this,2));
			liked_RV.setAdapter(recipeCardAdapter);
		}


		drawerLayout = findViewById(R.id.dlLiked);
		navigationView = findViewById(R.id.nvLiked);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
		navigationView.bringToFront();
		navigationView.setNavigationItemSelectedListener(this);
		navigationView.setCheckedItem(R.id.menuItem_liked);

		loginDBHelper = new LoginDBHelper(this);
		String username = loginDBHelper.getUserName(user_id);
		View header = navigationView.getHeaderView(0);
		menu_username = (TextView)header.findViewById(R.id.menu_username);
		menu_username.setText(username);

		menu_image = findViewById(R.id.im_menuLiked);
		onMenuClick();
	}

	private void onMenuClick() {
		menu_image.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View view) {
				drawerLayout.openDrawer(GravityCompat.START);
			}
		});
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menuItem_home:
				Intent intent = new Intent(likedActivity, HomeActivity.class);
				intent.putExtra("user_id", user_id);
				startActivity(intent);
				likedActivity.finish();
				break;

			case R.id.menuItem_categories:
				Intent intent1 = new Intent(likedActivity, CategoryActivity.class);
				intent1.putExtra("user_id", user_id);
				startActivity(intent1);
				likedActivity.finish();
				break;

			case R.id.menuItem_liked:
				break;

			case R.id.menuItem_ex:
				Intent intent_2 = new Intent(likedActivity, StartActivity.class);
				startActivity(intent_2);
				likedActivity.finish();
				break;
		}
		drawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}
}
