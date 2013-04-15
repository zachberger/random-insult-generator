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
	private DatabaseTemplate dbHelper;
	private String[] allColumns = { FavoritesTemplate.COLUMN_ID,
			FavoritesTemplate.COLUMN_CATEGORY,
			FavoritesTemplate.COLUMN_TEMPLATE };


	public FavoritesDAO(Context context) {
		dbHelper = new DatabaseTemplate(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}

	public void populateDatabase() {
		if(!dbHelper.getDatabaseInitialized()) {
			dbHelper.initializeDatabase();
		}
	}

	public Template createFavorite(String value, String category) {
		ContentValues values = new ContentValues();
		values.put(FavoritesTemplate.COLUMN_TEMPLATE, value);
		values.put(FavoritesTemplate.COLUMN_CATEGORY, category);
		long insertId = database.insert(FavoritesTemplate.TABLE_NAME, null,
				values);
		Cursor cursor = database.query(FavoritesTemplate.TABLE_NAME,
				allColumns, null, null,
				null, null, null);
		cursor.moveToFirst();
		Template newTemplete = cursorToTemplate(cursor);
		cursor.close();
		return newTemplete;
	}

	public void deleteTemplate(Template template) {
		long id = template.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(FavoritesTemplate.TABLE_NAME, FavoritesTemplate.COLUMN_ID
				+ " = " + id, null);
	}

	private Template cursorToTemplate(Cursor cursor) {
		Template comment = new Template();
		comment.setId(cursor.getLong(0));
		comment.setCategory(cursor.getString(1));
		comment.setTemplate(cursor.getString(2));
		return comment;
	}

	public List<Template> getAllFavorites() {
		List<Template> comments = new ArrayList<Template>();

		Cursor cursor = database.query(FavoritesTemplate.TABLE_NAME,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Template comment = cursorToTemplate(cursor);
			comments.add(comment);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}
}
