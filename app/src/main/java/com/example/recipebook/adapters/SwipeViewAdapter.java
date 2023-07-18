package com.example.recipebook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.recipebook.R;
import com.example.recipebook.models.SwipeViewModel;

import java.util.List;

public class SwipeViewAdapter extends PagerAdapter {

	private List<SwipeViewModel> models;
	private LayoutInflater layoutInflater;
	private Context context;

	public SwipeViewAdapter(List<SwipeViewModel> models, Context context) {
		this.models = models;
		this.context = context;
	}

	@Override
	public int getCount() {
		return models.size();
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return view.equals(object);
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.model_start_cards, container, false);

		ImageView imageView;
		TextView title;

		imageView = view.findViewById(R.id.start_image);
		title = view.findViewById(R.id.app_description);


		imageView.setImageResource(models.get(position).getImage());
		title.setText(models.get(position).getTitle());

		container.addView(view, 0);

		return view;
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		container.removeView((View)object);
	}
}
