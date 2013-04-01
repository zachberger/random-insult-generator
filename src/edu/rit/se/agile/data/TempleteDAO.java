package edu.rit.se.agile.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TempleteDAO {
	private SQLiteDatabase database;
	private DatabaseTemplete dbHelper;
	private String[] allColumns = { DatabaseTemplete.COLUMN_ID,
			DatabaseTemplete.COLUMN_TEMPLETE };


	public TempleteDAO(Context context) {
		dbHelper = new DatabaseTemplete(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}


	public Templete createTempelete(String value) {
		ContentValues values = new ContentValues();
		values.put(dbHelper.COLUMN_TEMPLETE, value);
		long insertId = database.insert(dbHelper.TABLE_NAME, null,
				values);
		Cursor cursor = database.query(dbHelper.TABLE_NAME,
				allColumns, dbHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Templete newTemplete = cursorToComment(cursor);
		cursor.close();
		return newTemplete;
	}

	public void deleteComment(Templete templete) {
		long id = templete.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(dbHelper.TABLE_NAME, dbHelper.COLUMN_ID
				+ " = " + id, null);
	}

	private Templete cursorToComment(Cursor cursor) {
		Templete comment = new Templete();
		comment.setId(cursor.getLong(0));
		comment.setTemplete(cursor.getString(1));
		return comment;
	}

	public List<Templete> getAllTemplates() {
		List<Templete> comments = new ArrayList<Templete>();

		Cursor cursor = database.query(dbHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Templete comment = cursorToComment(cursor);
			comments.add(comment);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}
}
