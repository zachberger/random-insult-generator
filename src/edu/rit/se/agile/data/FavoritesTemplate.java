package edu.rit.se.agile.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoritesTemplate extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "favorites";
	public static final String COLUMN_ID = "_id";
	public static final String FAVORITES_COLUMN = "favorite";

	private static final String DATABASE_NAME = "favorites.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " 
			+ FAVORITES_COLUMN + " text not null);";


	public FavoritesTemplate(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
}
