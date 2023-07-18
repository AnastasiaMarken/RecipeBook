package com.example.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.recipebook.adapters.SwipeViewAdapter;
import com.example.recipebook.models.SwipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

	public static StartActivity startActivity;
	private TextView sign_in;
	private TextView sign_up;


	private ViewPager viewPager;
	private SwipeViewAdapter swipeViewAdapter;
	private List<SwipeViewModel> models;
	private String [] start_desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);

		startActivity = this;

		sign_in = findViewById(R.id.sign_in);
		sign_up = findViewById(R.id.sign_up);

		initTextView();

		createView();
	}

	private void createView() {
		start_desc = getResources().getStringArray(R.array.start_description);

		models = new ArrayList<>();
		models.add(new SwipeViewModel(R.drawable.start_1, start_desc[0]));
		models.add(new SwipeViewModel(R.drawable.start_2, start_desc[1]));
		models.add(new SwipeViewModel(R.drawable.start_3, start_desc[2]));
		models.add(new SwipeViewModel(R.drawable.start_4, start_desc[3]));

		swipeViewAdapter = new SwipeViewAdapter(models, this);

		viewPager = findViewById(R.id.viewPager);
		viewPager.setAdapter(swipeViewAdapter);
		viewPager.setPadding(100,0,100,0);

//		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//			@Override
//			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//			}
//
//			@Override
//			public void onPageSelected(int position) {
//
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int state) {
//
//			}
//		});
	}

	private void initTextView() {

		sign_in.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(startActivity, LoginActivity.class);
				startActivity(intent);
			}
		});

		sign_up.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(startActivity, RegistrationActivity.class);
				startActivity(intent);
			}
		});
	}
}
