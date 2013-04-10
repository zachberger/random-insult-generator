package edu.rit.se.agile.randominsultapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.rit.se.agile.data.TemplateDAO;
import edu.rit.se.agile.data.WordDAO;

public class GenericActivity extends Activity {

	public GenericActivity() {
		super();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.random_insults, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent i = null;
		switch (item.getItemId()) {
		case R.id.menu_view_favorites:
			i = new Intent(this, ViewFavorites.class);
			break;
		case R.id.menu_add_word:
			i = new Intent(this, AddWord.class);
			break;
		case R.id.menu_generate_insults:
			i = new Intent(this, RandomInsults.class);
			break;
	    }
		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RandomInsults.wordDAO = new WordDAO(this);
		RandomInsults.templateDAO = new TemplateDAO(this);
		
		RandomInsults.wordDAO.open();
		RandomInsults.templateDAO.open();
		
//		RandomInsults.wordDAO.populateDatabase();
		RandomInsults.templateDAO.populateDatabase();
		
//		RandomInsults.templateDAO.createTemplate("This is an insult", "Default");
	}
	@Override
	public void onPause() {
		super.onPause();
		RandomInsults.wordDAO.close();
		RandomInsults.templateDAO.close();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		RandomInsults.wordDAO.close();
		RandomInsults.templateDAO.close();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		RandomInsults.wordDAO.open();
		RandomInsults.templateDAO.open();
	}
}