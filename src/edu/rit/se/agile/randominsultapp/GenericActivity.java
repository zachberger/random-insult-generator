package edu.rit.se.agile.randominsultapp;


import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import edu.rit.se.agile.data.FavoritesDAO;
import edu.rit.se.agile.data.TemplateDAO;
import edu.rit.se.agile.data.WordDAO;

public class GenericActivity extends Activity {
	public static FavoritesDAO favoritesDAO;

	public static WordDAO wordDAO;
	public static TemplateDAO templateDAO;

	protected static TextToSpeech tts;

	public GenericActivity() {
		super();
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		wordDAO = new WordDAO(this);
		templateDAO = new TemplateDAO(this);
		favoritesDAO = new FavoritesDAO(this);
		
		wordDAO.open();
		templateDAO.open();
		favoritesDAO.open();
		
		wordDAO.populateDatabase();
		templateDAO.populateDatabase();
		
		tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				
			}
		});
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		wordDAO.close();
		templateDAO.close();
		favoritesDAO.close();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		wordDAO.close();
		templateDAO.close();
		favoritesDAO.close();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		wordDAO.open();
		templateDAO.open();
		favoritesDAO.open();
	}
}
