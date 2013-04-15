package edu.rit.se.agile.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FavoritesDAO {
	private SQLiteDatabase database;
	private FavoritesTemplate dbHelper;
	private String[] allColumns = { FavoritesTemplate.COLUMN_ID,
			FavoritesTemplate.FAVORITES_COLUMN };


	public FavoritesDAO(Context context) {
		dbHelper = new FavoritesTemplate(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public String createFavorite(String value) {
		ContentValues values = new ContentValues();
		values.put(FavoritesTemplate.FAVORITES_COLUMN, value);
		long insertId = database.insert(FavoritesTemplate.TABLE_NAME, null,
				values);
		Cursor cursor = database.query(FavoritesTemplate.TABLE_NAME,
				allColumns, null, null,
				null, null, null);
		cursor.moveToFirst();
		String favorite = cursorToFavorite(cursor);
		cursor.close();
		return favorite;
	}

	public void deleteTemplate(Template template) {
		long id = template.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(FavoritesTemplate.TABLE_NAME, FavoritesTemplate.COLUMN_ID
				+ " = " + id, null);
	}

	private String cursorToFavorite(Cursor cursor) {
		String toReturn = "" + cursor.getString(1);
		return toReturn;
	}

	public List<String> getAllFavorites() {
		List<String> comments = new ArrayList<String>();

		Cursor cursor = database.query(FavoritesTemplate.TABLE_NAME,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String favorite = cursorToFavorite(cursor);
			comments.add(favorite);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}
	
	public Cursor getAllFavoritesCursor() {
		return database.query(FavoritesTemplate.TABLE_NAME,	allColumns, null, null, null, null, null);
	}
}
