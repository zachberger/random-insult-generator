package edu.rit.se.agile.randominsultapp;

import edu.rit.se.agile.data.FavoritesTemplate;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ViewFavorites extends GenericActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_favorites);
		
		ListView list =  (ListView) findViewById(R.id.favorites_list);
		Log.d("ViewFavorites", favoritesDAO.getAllFavoritesCursor().getCount()+"");
		
		list.setAdapter(
				new SimpleCursorAdapter(this, 
						R.layout.favorites_list_entry, 
						favoritesDAO.getAllFavoritesCursor(), //FIX THIS 
						new String[]{ FavoritesTemplate.FAVORITES_COLUMN }, 
						new int[]{ R.id.favorites_list_entry }, 
						SimpleCursorAdapter.FLAG_AUTO_REQUERY ));
		}

}
