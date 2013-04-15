package edu.rit.se.agile.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoritesTemplate extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "favorites";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TEMPLATE = "template";
	public static final String COLUMN_CATEGORY = "category";

	private static final String DATABASE_NAME = "favorites.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " 
			+ COLUMN_CATEGORY + " text not null, " 
			+ COLUMN_TEMPLATE + " text not null);";

	private Context ctx;

	public FavoritesTemplate(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		this.ctx = context;
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
