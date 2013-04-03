package edu.rit.se.agile.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordsTemplate extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "words";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_WORD_TYPE = "word_type";
	public static final String COLUMN_WORD = "word";
	public static final String COLUMN_CATEGORY = "category";

	private static final String DATABASE_NAME = "words.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_WORD_TYPE + " text not null, "
			+ COLUMN_CATEGORY + " text not null, "
			+ COLUMN_WORD + " text not null);";

	//The seperator string for the csv file to seperate entries.
	private static final String SEPERATOR = ",";

	/*
	 * The filename for the words csv file.
	 * 
	 * Format of the file needs to one entry per line. each entry is as follows
	 * 
	 * id , word_type , category , word
	 * 
	 * 
	 */
	private static final String IMPORT_FILE_NAME = "words.csv";	

	public WordsTemplate(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
