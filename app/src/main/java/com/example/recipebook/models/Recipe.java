package com.example.recipebook.models;

import java.util.ArrayList;
import java.util.Arrays;

public class Recipe {

	private int id;
	private String recipe_title;
	private int category;
	private String time;
	private String ingredients;
	private String kol;
	private String steps;
	private int image;
	private ArrayList<String> Ingredients;
	private ArrayList<String> Kol;
	private ArrayList<String> Steps;

	public Recipe(){
	}

	public Recipe(int id, String recipe_title, int category, String time, String ingredients, String kol, String steps, int image) {
		this.id = id;
		this.recipe_title = recipe_title;
		this.category = category;
		this.time = time;
		this.ingredients = ingredients;
		this.kol = kol;
		this.steps = steps;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecipe_title() {
		return recipe_title;
	}

	public void setRecipe_title(String recipe_title) {
		this.recipe_title = recipe_title;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
		this.Ingredients = new ArrayList<>(Arrays.asList(ingredients.split("</n>")));
	}

	public String getKol() {
		return kol;
	}

	public void setKol(String kol) {
		this.kol = kol;
		this.Kol = new ArrayList<>(Arrays.asList(kol.split("</n>")));
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
		this.Steps = new ArrayList<>(Arrays.asList(steps.split("</n>")));
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public ArrayList<String> getListIngredients(){
		return Ingredients;
	}

	public ArrayList<String> getListKol(){
		return Kol;
	}

	public ArrayList<String> getListSteps(){
		return Steps;
	}
}
