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

}