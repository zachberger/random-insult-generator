package edu.rit.se.agile.randominsultapp;

import java.util.Collections;
import java.util.List;

import edu.rit.se.agile.data.Template;
import edu.rit.se.agile.data.WordsTemplate;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.LoginButton;
import com.facebook.widget.WebDialog;

public class GenerateInsultsFragment extends Fragment {
	
	private Button generateButton;
	private Button facebookShareButton;
	private TextView insultTextField;
	private Spinner categorySpinner;
	private BroadcastReceiver receiver;
	private WebDialog dialog;
	private UiLifecycleHelper uiHelper;
	private LoginButton loginButton;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View generatorView = inflater.inflate(R.layout.activity_random_insults, container, false);
		insultTextField = (TextView) generatorView.findViewById( R.id.insult_display );
		generateButton = (Button) generatorView.findViewById(R.id.button_generate);
		categorySpinner = (Spinner) generatorView.findViewById(R.id.category_spinner);
		facebookShareButton = (Button) generatorView.findViewById(R.id.button_share);
		loginButton  = (LoginButton) generatorView.findViewById(R.id.authButton);
		loginButton.setFragment(this);
		
		facebookShareButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Session s = Session.getActiveSession();
				if( s != null && s.isOpened() ){
					Bundle params = new Bundle();

					params.putString("name", "I'm an assole");
					params.putString("caption", "I generated an insult:");
					params.putString("description", insultTextField.getText().toString());
					showDialogWithoutNotificationBar("feed", params);

				}
			}
			
		});
		
		Cursor categoryCursor = RandomInsults.wordDAO.getCategories();
		categorySpinner.setAdapter(
				new SimpleCursorAdapter(getActivity(), 
						android.R.layout.simple_spinner_dropdown_item, 
						categoryCursor, 
						new String[]{ WordsTemplate.COLUMN_CATEGORY }, 
						new int[]{ android.R.id.text1 }, 
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  uiHelper.onActivityResult(requestCode, resultCode, data);
	  Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();

	    uiHelper.onDestroy();
		LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
		
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	    	loginButton.setVisibility(View.INVISIBLE);
	    	facebookShareButton.setVisibility(View.VISIBLE);
	        Log.i("GenerateInsultsFragment", "Logged in...");
	    } else if (state.isClosed()) {
	    	loginButton.setVisibility(View.INVISIBLE);
	    	facebookShareButton.setVisibility(View.VISIBLE);
	        Log.i("GenerateInsultsFragment", "Logged out...");
	    }
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
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
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	private void showDialogWithoutNotificationBar(String action, Bundle params){
		dialog = new WebDialog.Builder(getActivity(), Session.getActiveSession(), action, params).
			    setOnCompleteListener(new WebDialog.OnCompleteListener() {
			    @Override
			    public void onComplete(Bundle values, FacebookException error) {

			    }
			}).build();

			Window dialog_window = dialog.getWindow();
			dialog_window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			    WindowManager.LayoutParams.FLAG_FULLSCREEN);

			dialog.show();
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

}
