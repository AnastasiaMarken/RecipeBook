package com.example.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.DBHelpers.LoginDBHelper;
import com.example.recipebook.DBHelpers.RecipesDBHelper;
import com.example.recipebook.adapters.RecipeCardAdapter;
import com.example.recipebook.models.Recipe;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	public static HomeActivity homeActivity;

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
		setContentView(R.layout.home_activity);

		homeActivity = this;

		Intent intent = getIntent();
		user_id = intent.getExtras().getInt("user_id");

		recipesDBHelper = new RecipesDBHelper(this);
		try {
			recipesDBHelper.updateDataBase();
		} catch (IOException mIOException) {
			throw new Error("UnableToUpdateDatabase");
		}

		Recipes = recipesDBHelper.getRecipes();
		RecyclerView home_RV = (RecyclerView) findViewById(R.id.home_RV);
		recipeCardAdapter = new RecipeCardAdapter(this, Recipes, user_id, 1);
		home_RV.setLayoutManager(new GridLayoutManager(this,2));
		home_RV.setAdapter(recipeCardAdapter);


		drawerLayout = findViewById(R.id.drawerLayout);
		navigationView = findViewById(R.id.navigation_view);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();
		navigationView.bringToFront();
		navigationView.setNavigationItemSelectedListener(this);
		navigationView.setCheckedItem(R.id.menuItem_home);


		loginDBHelper = new LoginDBHelper(this);
		String username = loginDBHelper.getUserName(user_id);
		View header = navigationView.getHeaderView(0);
		menu_username = (TextView)header.findViewById(R.id.menu_username);
		menu_username.setText(username);


		menu_image = findViewById(R.id.im_menu);
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
				break;

			case R.id.menuItem_categories:
				Intent intent = new Intent(homeActivity, CategoryActivity.class);
				intent.putExtra("user_id", user_id);
				startActivity(intent);
				homeActivity.finish();
				break;

			case R.id.menuItem_liked:
				Intent intent_1 = new Intent(homeActivity, LikedActivity.class);
				intent_1.putExtra("user_id", user_id);
				startActivity(intent_1);
				homeActivity.finish();
				break;

			case R.id.menuItem_ex:
				Intent intent_2 = new Intent(homeActivity, StartActivity.class);
				startActivity(intent_2);
				homeActivity.finish();
				break;
		}
		drawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}
}
