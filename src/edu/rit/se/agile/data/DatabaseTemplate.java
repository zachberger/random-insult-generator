package edu.rit.se.agile.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseTemplate extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "template";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TEMPLATE = "template";
	public static final String COLUMN_CATEGORY = "category";
	
	private static final String DATABASE_NAME = "templates.db";
	private static final int DATABASE_VERSION = 1;
	
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
	      + TABLE_NAME + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " 
	      + COLUMN_CATEGORY + " text not null, " 
	      + COLUMN_TEMPLATE + " text not null);";
	
	//The seperator string for the csv file to seperate entries.
		private static final String SEPERATOR = ",";
		
		/*
		 * The filename for the template csv file.
		 * 
		 * Format of the file needs to one entry per line. each entry is as follows
		 * 
		 * id , category , template
		 * 
		 * 
		 * NOTE: The templates cannot have ',' in them, if they do we need to change our seperator.
		 * 
		 * 
		 */
		private static final String IMPORT_FILE_NAME = "template.csv";	

	public DatabaseTemplate(Context context) {
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
