package com.example.recipebook.models;

import java.util.ArrayList;

public class Commentary {
	private int id;
	private int recipe_id;
	private int user_id;
	private String date;
	private String commentary;

	public Commentary(int id, int user_id, int recipe_id, String commentary, String date) {
		this.id = id;
		this.recipe_id = recipe_id;
		this.user_id = user_id;
		this.date = date;
		this.commentary = commentary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCommentary() {
		return commentary;
	}

	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}
}
