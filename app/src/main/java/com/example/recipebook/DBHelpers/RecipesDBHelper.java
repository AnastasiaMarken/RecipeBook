package com.example.recipebook.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.recipebook.R;
import com.example.recipebook.models.Commentary;
import com.example.recipebook.models.Recipe;

import org.w3c.dom.Comment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecipesDBHelper extends SQLiteOpenHelper {

	private static String DB_PATH; // полный путь к базе данных
	private static String DB_NAME = "Recipes.db";
	public static final int DB_VERSION = 1;

	public static final String TABLE_NAME = "RECIPES";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_RECIPENAME = "recipeName";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_INGREDIENTS = "ingredients";
	public static final String COLUMN_KOL = "kol";
	public static final String COLUMN_STEPS = "steps";



	public static final String TABLE_NAME_COMMENTARY = "COMMENTARY";
	public static final String COLUMN_ID_COMMENTARY = "_id";
	public static final String COLUMN_USER_ID_COMMENTARY = "user_id";
	public static final String COLUMN_RECIPE_ID_COMMENTARY = "recipe_id";
	public static final String COLUMN_COMMENTARY = "commentary";
	public static final String COLUMN_DATE_COMMENTARY = "date";

	public static final String COLUMN_CATEGORY_ID_CATEGORIES = "_id";
	public static final String COLUMN_CATEGORY_NAME_CATEGORIES = "category_name";


	private SQLiteDatabase mDataBase;
	private final Context mContext;
	private boolean mNeedUpdate = false;

	public RecipesDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		if (android.os.Build.VERSION.SDK_INT >= 17)
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
		else
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		this.mContext = context;

		copyDataBase();

		this.getReadableDatabase();
	}

	public void updateDataBase() throws IOException {
		if (mNeedUpdate) {
			File dbFile = new File(DB_PATH + DB_NAME);
			if (dbFile.exists())
				dbFile.delete();

			copyDataBase();

			mNeedUpdate = false;
		}
	}

	private boolean checkDataBase() {
		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}

	private void copyDataBase() {
		if (!checkDataBase()) {
			this.getReadableDatabase();
			this.close();
			try {
				copyDBFile();
			} catch (IOException mIOException) {
				throw new Error("ErrorCopyingDataBase");
			}
		}
	}

	private void copyDBFile() throws IOException {
		InputStream mInput = mContext.getAssets().open(DB_NAME);
		OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
		byte[] mBuffer = new byte[1024];
		int mLength;
		while ((mLength = mInput.read(mBuffer)) > 0)
			mOutput.write(mBuffer, 0, mLength);
		mOutput.flush();
		mOutput.close();
		mInput.close();
	}

	public boolean openDataBase() throws SQLException {
		mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
		return mDataBase != null;
	}

	@Override
	public synchronized void close() {
		if (mDataBase != null)
			mDataBase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion)
			mNeedUpdate = true;
	}

	public ArrayList<Recipe> getRecipes(){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Recipe> Recipes = new ArrayList<>();
		Cursor cursor = db.rawQuery("SELECT * FROM RECIPES", null);
		cursor.moveToLast();

		//Пробегаем по всем рецептам
		while (!cursor.isBeforeFirst()) {
			Recipe recipe = new Recipe();
			//Заполняем рецепт
			int id = cursor.getInt(0);
			recipe.setId(id);

			String recipeName = cursor.getString(1);
			recipe.setRecipe_title(recipeName);

			int category = cursor.getInt(2);
			recipe.setCategory(category);

			String time = cursor.getString(3);
			recipe.setTime(time);

			String ingredients = cursor.getString(4);
			recipe.setIngredients(ingredients);

			String kol = cursor.getString(5);
			recipe.setKol(kol);

			String steps = cursor.getString(6);
			recipe.setSteps(steps);

			Recipes.add(recipe);

			cursor.moveToPrevious();
		}
		cursor.close();



		Recipes.get(26).setImage(R.drawable.recipe_1);
		Recipes.get(25).setImage(R.drawable.recipe_2);
		Recipes.get(24).setImage(R.drawable.recipe_3);
		Recipes.get(23).setImage(R.drawable.recipe_4);
		Recipes.get(22).setImage(R.drawable.recipe_5);
		Recipes.get(21).setImage(R.drawable.recipe_6);
		Recipes.get(20).setImage(R.drawable.recipe_7);
		Recipes.get(19).setImage(R.drawable.recipe_8);
		Recipes.get(18).setImage(R.drawable.recipe_9);
		Recipes.get(17).setImage(R.drawable.recipe_10);
		Recipes.get(16).setImage(R.drawable.recipe_11);
		Recipes.get(15).setImage(R.drawable.recipe_12);
		Recipes.get(14).setImage(R.drawable.recipe_13);
		Recipes.get(13).setImage(R.drawable.recipe_14);
		Recipes.get(12).setImage(R.drawable.recipe_15);
		Recipes.get(11).setImage(R.drawable.recipe_16);
		Recipes.get(10).setImage(R.drawable.recipe_17);
		Recipes.get(9).setImage(R.drawable.recipe_18);
		Recipes.get(8).setImage(R.drawable.recipe_19);
		Recipes.get(7).setImage(R.drawable.recipe_20);
		Recipes.get(6).setImage(R.drawable.recipe_21);
		Recipes.get(5).setImage(R.drawable.recipe_22);
		Recipes.get(4).setImage(R.drawable.recipe_23);
		Recipes.get(3).setImage(R.drawable.recipe_24);
		Recipes.get(2).setImage(R.drawable.recipe_25);
		Recipes.get(1).setImage(R.drawable.recipe_26);
		Recipes.get(0).setImage(R.drawable.recipe_27);



		return Recipes;
	}

	public ArrayList<Recipe> getLikedRecipes(int user_id){
		ArrayList<Recipe> LikedRecipes = new ArrayList<>();

		ArrayList<Recipe> AllRecipes = this.getRecipes();

		LoginDBHelper loginDBHelper = new LoginDBHelper(mContext);
		ArrayList<String> Liked = loginDBHelper.getLiked(user_id);

		for(int i = 0; i < AllRecipes.size(); i++){
			for(int j = 0; j < Liked.size(); j++){
				if(Liked.get(j).equals(String.valueOf(AllRecipes.get(i).getId()))){
					LikedRecipes.add(AllRecipes.get(i));
				}
			}
		}
		return LikedRecipes;
	}


	public Boolean insertCommentary(int user_id, int recipe_id, String commentary){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		Date utilDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		String date = dateFormat.format(utilDate);

		values.put(COLUMN_USER_ID_COMMENTARY, user_id);
		values.put(COLUMN_RECIPE_ID_COMMENTARY, recipe_id);
		values.put(COLUMN_COMMENTARY, commentary);
		values.put(COLUMN_DATE_COMMENTARY, date);

		long result = db.insertOrThrow("COMMENTARY", null, values);
		if (result == -1) return false;
		else return true;
	}

	public ArrayList<Commentary> getCommentary(int recipe_id){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Commentary> Commentaries = new ArrayList<>();
		Cursor cursor = db.rawQuery("SELECT * FROM COMMENTARY", null);
		cursor.moveToLast();

		//Пробегаем по всем рецептам
		while (!cursor.isBeforeFirst()) {
			//Заполняем
			if(cursor.getInt(2) == recipe_id){
				Commentary commentary = new Commentary(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3), cursor.getString(4));
				Commentaries.add(commentary);
			}
			cursor.moveToPrevious();
		}
		cursor.close();
		return Commentaries;
	}

	public ArrayList<String> getCategories(){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<String> Categories = new ArrayList<>();
		Cursor cursor = db.rawQuery("SELECT * FROM CATEGORIES", null);
		cursor.moveToFirst();

		//Пробегаем по всем рецептам
		while (!cursor.isAfterLast()) {
			//Заполняем
			String category = cursor.getString(1);
			Categories.add(category);
			cursor.moveToNext();
		}
		cursor.close();
		return Categories;
	}
}