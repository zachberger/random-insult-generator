package edu.rit.se.agile.randominsultapp;

import java.util.Collections;
import java.util.List;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import edu.rit.se.agile.data.Template;
import edu.rit.se.agile.data.WordsTemplate;

public class GenerateInsultsFragment extends Fragment {
	
	private Button generateButton;
	private Button facebookShareButton;
	private TextView insultTextField;
	private Spinner categorySpinner;
	private BroadcastReceiver receiver;
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
				publishFeedDialog();
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
	
	private void publishFeedDialog() {
	    Bundle params = new Bundle();
	    params.putString("name", "I generated a random insult");
	    params.putString("caption", "This is a test.");
	    params.putString("description", insultTextField.getText().toString() );
	    params.putString("link", "https://developers.facebook.com/android");
	    params.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

	    WebDialog feedDialog = (
	        new WebDialog.FeedDialogBuilder(getActivity(),
	            Session.getActiveSession(),
	            params))
	        .setOnCompleteListener(new OnCompleteListener() {

	            @Override
	            public void onComplete(Bundle values,
	                FacebookException error) {
	                if (error == null) {
	                    // When the story is posted, echo the success
	                    // and the post Id.
	                    final String postId = values.getString("post_id");
	                    if (postId != null) {
	                        Toast.makeText(getActivity(),
	                            "Posted story, id: "+postId,
	                            Toast.LENGTH_SHORT).show();
	                    } else {
	                        // User clicked the Cancel button
	                        Toast.makeText(getActivity().getApplicationContext(), 
	                            "Publish cancelled", 
	                            Toast.LENGTH_SHORT).show();
	                    }
	                } else if (error instanceof FacebookOperationCanceledException) {
	                    // User clicked the "x" button
	                    Toast.makeText(getActivity().getApplicationContext(), 
	                        "Publish cancelled", 
	                        Toast.LENGTH_SHORT).show();
	                } else {
	                    // Generic, ex: network error
	                    Toast.makeText(getActivity().getApplicationContext(), 
	                        "Error posting story", 
	                        Toast.LENGTH_SHORT).show();
	                }
	            }

	        })
	        .build();
	    feedDialog.show();
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
