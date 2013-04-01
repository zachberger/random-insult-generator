package edu.rit.se.agile.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseTemplete extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "templete";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TEMPLETE = "templete";
	
	private static final String DATABASE_NAME = "templetes.db";
	private static final int DATABASE_VERSION = 1;
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
	      + TABLE_NAME + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_TEMPLETE
	      + " text not null);";

	public DatabaseTemplete(Context context) {
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
