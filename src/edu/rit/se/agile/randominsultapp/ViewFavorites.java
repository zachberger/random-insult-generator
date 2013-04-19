package edu.rit.se.agile.randominsultapp;

import edu.rit.se.agile.data.FavoritesTemplate;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ViewFavorites extends GenericActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_favorites);
		
		ListView list =  (ListView) findViewById(R.id.favorites_list);
		Log.d("ViewFavorites", favoritesDAO.getAllFavoritesCursor().getCount()+"");
		
		list.setAdapter( new FavoritesListAdapter() );
		list.setClickable(true);
		list.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("ViewFavorites", "PAUSED");				
			}
			
		});
	}
		
	private class FavoritesListAdapter extends SimpleCursorAdapter{
		
		public FavoritesListAdapter(){
			super( ViewFavorites.this, 
					R.layout.favorites_list_entry, 
					favoritesDAO.getAllFavoritesCursor(), //FIX THIS 
					new String[]{ FavoritesTemplate.FAVORITES_COLUMN }, 
					new int[]{ R.id.favorites_list_entry }, 
					SimpleCursorAdapter.FLAG_AUTO_REQUERY );
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			ImageButton playButton = (ImageButton) view.findViewById(R.id.generateTtsButton);
			final TextView savedInsult = (TextView) view.findViewById( R.id.favorites_list_entry );
			playButton.setOnClickListener( new OnClickListener(){

				@Override
				public void onClick(View triggerView) {
					tts.speak(savedInsult.getText().toString(), 
							TextToSpeech.QUEUE_FLUSH, 
							null);
				}
				
			});
			return view;
		}
	}

}
