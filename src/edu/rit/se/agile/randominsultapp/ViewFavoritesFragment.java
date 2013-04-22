package edu.rit.se.agile.randominsultapp;

import edu.rit.se.agile.data.FavoritesTemplate;
import android.app.Fragment;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ViewFavoritesFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View favoritesView = inflater.inflate(R.layout.activity_view_favorites, container, false);
		
		ListView list =  (ListView) favoritesView.findViewById(R.id.favorites_list);
		Log.d("ViewFavorites", RandomInsults.favoritesDAO.getAllFavoritesCursor().getCount()+"");
		
		list.setAdapter( new FavoritesListAdapter() );
		list.setClickable(true);
		list.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("ViewFavorites", "PAUSED");				
			}
			
		});
		return favoritesView;
	}
		
	private class FavoritesListAdapter extends SimpleCursorAdapter{
		
		public FavoritesListAdapter(){
			super( getActivity(), 
					R.layout.favorites_list_entry, 
					RandomInsults.favoritesDAO.getAllFavoritesCursor(), //FIX THIS 
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
					RandomInsults.tts.speak(savedInsult.getText().toString(), 
							TextToSpeech.QUEUE_FLUSH, 
							null);
				}
				
			});
			return view;
		}
	}

}
