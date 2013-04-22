package edu.rit.se.agile.randominsultapp;

import java.util.Collections;
import java.util.List;

import edu.rit.se.agile.data.Template;
import edu.rit.se.agile.data.WordsTemplate;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GenerateInsultsFragment extends Fragment {
	
	private Button generateButton;
	private TextView insultTextField;
	private Spinner categorySpinner;
	private BroadcastReceiver receiver;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View generatorView = inflater.inflate(R.layout.activity_random_insults, container, false);
		insultTextField = (TextView) generatorView.findViewById( R.id.insult_display );
		generateButton = (Button) generatorView.findViewById(R.id.button_generate);
		categorySpinner = (Spinner) generatorView.findViewById(R.id.category_spinner);
		
		Cursor categoryCursor = RandomInsults.wordDAO.getCategories();
		categorySpinner.setAdapter(
				new SimpleCursorAdapter(getActivity(), 
						R.layout.category_list, 
						categoryCursor, 
						new String[]{ WordsTemplate.COLUMN_CATEGORY }, 
						new int[]{ R.id.category_list_entry }, 
						SimpleCursorAdapter.FLAG_AUTO_REQUERY ));
		generateButton.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				String category = ((Cursor) categorySpinner.getSelectedItem()).getString(0); //My spinnah thing
				List<Template> temp = RandomInsults.templateDAO.getAllTemplates(category); 

				Collections.shuffle(temp);
				if(temp.size() > 0 ) {
					String text = temp.get(0).fillTemplate(RandomInsults.wordDAO,category).trim();
					text = Character.toUpperCase(text.charAt(0)) + text.substring(1);
					insultTextField.setText(text);
				} else {
					insultTextField.setText("There was an error! :(");
				}
			}

		});

		return generatorView;
	}
	

	@Override
	public void onDestroy(){
		super.onDestroy();
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		receiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context context, Intent intent) {
				if( intent.getExtras().getBoolean("save-favorite") ){
					RandomInsults.favoritesDAO.createFavorite(insultTextField.getText().toString());
					Toast.makeText(getActivity(), 
							"Saved to favorites.", 
							Toast.LENGTH_LONG).show();
				}else if( intent.getExtras().getBoolean("speak-insult") ){
					RandomInsults.tts.speak(insultTextField.getText().toString(), 
					TextToSpeech.QUEUE_FLUSH, 
					null);
				}
			}
			
		};
		
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver( receiver, new IntentFilter("action-bar-pressed") );
	}

}
