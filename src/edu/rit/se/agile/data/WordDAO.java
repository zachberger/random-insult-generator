package edu.rit.se.agile.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WordDAO {
	private SQLiteDatabase database;
	private WordsTemplate dbHelper;
	private String[] allColumns = { WordsTemplate.COLUMN_ID,
			WordsTemplate.COLUMN_WORD_TYPE, 
			WordsTemplate.COLUMN_WORD};
	
	public WordDAO(Context context) {
		dbHelper = new WordsTemplate(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
	}
	
	public Word createWord(String type, String value) {
		ContentValues values = new ContentValues();
		values.put(dbHelper.COLUMN_WORD, value);
		values.put(dbHelper.COLUMN_WORD_TYPE, type);
		long insertId = database.insert(dbHelper.TABLE_NAME, null,
				values);
		Cursor cursor = database.query(dbHelper.TABLE_NAME,
				allColumns, dbHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Word newWord = cursorToWord(cursor);
		cursor.close();
		return newWord;
	}

	public void deleteWord(Word word) {
		long id = word.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(dbHelper.TABLE_NAME, dbHelper.COLUMN_ID
				+ " = " + id, null);
	}

	private Word cursorToWord(Cursor cursor) {
		Word comment = new Word();
		comment.setId(cursor.getLong(0));
		comment.setType(cursor.getString(1));
		comment.setWord(cursor.getString(2));
		return comment;
	}

	public List<Word> getAllWords() {
		List<Word> comments = new ArrayList<Word>();

		Cursor cursor = database.query(dbHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Word comment = cursorToWord(cursor);
			comments.add(comment);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return comments;
	}

}
