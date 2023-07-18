package com.example.recipebook.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class LoginDBHelper extends SQLiteOpenHelper {

	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "RecipeBook.db";

	public static final String TABLE_NAME = "USERS";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_PASS = "password";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_LIKED = "liked";

	public LoginDBHelper(@Nullable Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String newDB = "CREATE TABLE " + TABLE_NAME + " ("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_USERNAME + " TEXT, "
				+ COLUMN_PASS + " TEXT, "
				+ COLUMN_EMAIL + " TEXT, "
				+ COLUMN_LIKED + " TEXT);";
		db.execSQL("create Table USERS(_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, email TEXT, liked TEXT)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int i, int i1) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}

	public Boolean insertData(String username, String password, String email){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(COLUMN_USERNAME, username);
		values.put(COLUMN_PASS, password);
		values.put(COLUMN_EMAIL, email);

		long result = db.insert(TABLE_NAME, null, values);
		if (result == -1) return false;
		else return true;
	}

	public Boolean checkUsername(String username){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where (" + COLUMN_USERNAME + " = ?)", new String[] {username});

		if (cursor.getCount() > 0) return true;
		else return false;
	}

	public Boolean checkUsernameAndPassword(String username, String password){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where (" + COLUMN_USERNAME + " = ? and "+ COLUMN_PASS + " = ?)",
				new String[] {username, password});

		if (cursor.getCount() > 0) return true;
		else return false;
	}

	public ArrayList<String> getLiked(int user_id){
		SQLiteDatabase db = this.getWritableDatabase();
		ArrayList<String> Liked;

		Cursor cursor = db.rawQuery("SELECT * FROM USERS", null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast() && (user_id != cursor.getInt(0))) {
			cursor.moveToNext();
		}
		String liked = cursor.getString(4);
		cursor.close();

		if(liked == null) {
			Liked = new ArrayList<>();
		} else Liked = new ArrayList<>(Arrays.asList(liked.split("</n>")));

		for(int i = 0; i < Liked.size(); i++){
			if (Liked.get(i).equals("")) Liked.remove(i);
		}
		return Liked;
	}

	public int getUserId(String name){
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM USERS", null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast() && !name.equals(cursor.getString(1))) {
			cursor.moveToNext();
		}
		int user_id = cursor.getInt(0);
		cursor.close();

		return user_id;
	}

	public void updateLiked(int id, String liked){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_LIKED, liked);
		db.update(TABLE_NAME, values, COLUMN_ID + "= ?", new String[]{String.valueOf(id)});
	}

	public String getUserName(int user_id){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM USERS", null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast() && (user_id != cursor.getInt(0))) {
			cursor.moveToNext();
		}
		String username = cursor.getString(1);
		cursor.close();

		return username;
	}
}
