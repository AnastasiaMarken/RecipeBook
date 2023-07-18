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

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.ViewHolder> {

	private ArrayList<String> Steps;
	private Context context;

	public class ViewHolder extends RecyclerView.ViewHolder{

		TextView step;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			step = itemView.findViewById(R.id.model_ListSteps);
		}
	}

	public StepsRecyclerViewAdapter(Context context, ArrayList<String> Steps){
		this.context = context;
		this.Steps = Steps;
	}

	@Override
	public int getItemCount() {
		return Steps.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@NonNull
	@Override
	public StepsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_steps, parent, false);
		StepsRecyclerViewAdapter.ViewHolder ivh = new StepsRecyclerViewAdapter.ViewHolder(view);
		return ivh;
	}

	@Override
	public void onBindViewHolder(@NonNull StepsRecyclerViewAdapter.ViewHolder holder, int position) {
		holder.step.setText(Steps.get(position));
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

}
