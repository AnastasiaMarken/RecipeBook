package com.example.recipebook.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipebook.DBHelpers.LoginDBHelper;
import com.example.recipebook.DBHelpers.RecipesDBHelper;
import com.example.recipebook.R;
import com.example.recipebook.models.Commentary;

import java.util.ArrayList;

public class CommentaryAdapter extends RecyclerView.Adapter<CommentaryAdapter.CommentaryViewHolder>{

	private LoginDBHelper DB;
	private Context context;
	private ArrayList<Commentary> Commentaries;

	public class CommentaryViewHolder extends RecyclerView.ViewHolder{

		TextView user_name;
		TextView date;
		TextView commentary;

		public CommentaryViewHolder(@NonNull View itemView) {
			super(itemView);
			user_name = itemView.findViewById(R.id.model_commentary_username);
			date = itemView.findViewById(R.id.model_commentary_date);
			commentary = itemView.findViewById(R.id.model_commentary_text);
		}
	}

	public CommentaryAdapter(Context context, ArrayList<Commentary> Commentaries){
		this.context = context;
		this.Commentaries = Commentaries;
	}

	@Override
	public int getItemCount() {
		return Commentaries.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@NonNull
	@Override
	public CommentaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_commentary, parent, false);
		CommentaryViewHolder ivh = new CommentaryViewHolder(view);
		return ivh;
	}

	@Override
	public void onBindViewHolder(@NonNull CommentaryViewHolder holder, int position) {
		DB = new LoginDBHelper(context);
		String user_name_text = DB.getUserName(Commentaries.get(position).getUser_id());

		holder.user_name.setText(user_name_text);
		holder.date.setText(Commentaries.get(position).getDate());
		holder.commentary.setText(Commentaries.get(position).getCommentary());
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}
}
