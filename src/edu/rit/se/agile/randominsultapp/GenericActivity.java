package edu.rit.se.agile.randominsultapp;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

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
		switch (item.getItemId()) {
		case R.id.view_favorites:
			Intent intent = new Intent(this, ViewFavorites.class);
		    startActivity(intent);
			break;
		case R.id.action_add_word:
			Intent i = new Intent(this, AddWord.class);
			startActivity(i);
	    }
		
		return true;
	}

}