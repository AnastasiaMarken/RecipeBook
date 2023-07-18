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
import com.example.recipebook.adapters.CategoryAdapter;
import com.example.recipebook.adapters.RecipeCardAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	public static CategoryActivity categoryActivity;

	private LoginDBHelper loginDBHelper;
	private RecipesDBHelper recipesDBHelper;

	private CategoryAdapter categoryAdapter;
	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	private ArrayList<String> Categories_title;
	private TextView menu_username;
	private ImageView menu_image;
	private int user_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_activity);

		categoryActivity = this;

		Intent intent = getIntent();
		user_id = intent.getExtras().getInt("user_id");

		ArrayList<Integer> Categories = new ArrayList<>();
		Categories.add(R.drawable.main_food);
		Categories.add(R.drawable.soups);
		Categories.add(R.drawable.salad);
		Categories.add(R.drawable.breakfasts);
		Categories.add(R.drawable.bakery);
		Categories.add(R.drawable.tea);
		recipesDBHelper = new RecipesDBHelper(this);
		Categories_title = recipesDBHelper.getCategories();

		System.out.println(Categories.size());
		System.out.println(Categories_title.size());
		RecyclerView category_RV = (RecyclerView) findViewById(R.id.category_RV);
		categoryAdapter = new CategoryAdapter(this, Categories, Categories_title, user_id, 3);
		category_RV.setLayoutManager(new GridLayoutManager(this,2));
		category_RV.setAdapter(categoryAdapter);


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
				Intent intent = new Intent(categoryActivity, HomeActivity.class);
				intent.putExtra("user_id", user_id);
				startActivity(intent);
				categoryActivity.finish();
				break;

			case R.id.menuItem_categories:
				break;

			case R.id.menuItem_liked:
				Intent intent1 = new Intent(categoryActivity, LikedActivity.class);
				intent1.putExtra("user_id", user_id);
				startActivity(intent1);
				categoryActivity.finish();
				break;

			case R.id.menuItem_ex:
				Intent intent_2 = new Intent(categoryActivity, StartActivity.class);
				startActivity(intent_2);
				categoryActivity.finish();
				break;
		}
		drawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}
}
