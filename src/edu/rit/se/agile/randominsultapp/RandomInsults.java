package edu.rit.se.agile.randominsultapp;

import edu.rit.se.agile.data.FavoritesDAO;
import edu.rit.se.agile.data.TemplateDAO;
import edu.rit.se.agile.data.WordDAO;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class RandomInsults extends FragmentActivity {

	Menu menu;
	public static FavoritesDAO favoritesDAO;
	public static WordDAO wordDAO;
	public static TemplateDAO templateDAO;
	public static TextToSpeech tts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

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
		

	    Tab tab = actionBar.newTab()
	            .setText("Generate Insults")
	            .setTabListener(new TabListener<GenerateInsultsFragment>(
	                    this, "generate", GenerateInsultsFragment.class));
	    actionBar.addTab(tab);
	    
	    tab = actionBar.newTab()
	            .setText("Favorites")
	            .setTabListener(new TabListener<ViewFavoritesFragment>(
	                    this, "favorites", ViewFavoritesFragment.class));
	    actionBar.addTab(tab);
	    
	    tab = actionBar.newTab()
	            .setText("Customize")
	            .setTabListener(new TabListener<CustomizeFragment>(
	                    this, "customize", CustomizeFragment.class));
	    
	    actionBar.addTab(tab);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.random_insults, menu);
	    this.menu = menu;
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		Intent action = new Intent("action-bar-pressed");
	    switch (item.getItemId()) {
	        case R.id.menu_save_favorites:
	        	action.putExtra("save-favorite", true);
	        case R.id.menu_speak:
	        	action.putExtra("speak-insult", true);
	    }
    	LocalBroadcastManager.getInstance(this).sendBroadcast( action );
    	return true;
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

	public class TabListener<T extends Fragment> implements ActionBar.TabListener {
	    
		private Fragment mFragment;
	    private final Activity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;

	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Activity activity, String tag, Class<T> clz) {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	    }

	    /* The following are each of the ActionBar.TabListener callbacks */

	    public void onTabSelected(Tab tab, FragmentTransaction DONOTUSE) {
	    	android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	    	
	        // Check if the fragment is already initialized
	        if (mFragment == null) {
	            // If not, instantiate and add it to the activity
	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
	            ft.add(android.R.id.content, mFragment, mTag);
	        } else {
	            // If it exists, simply attach it in order to show it
	            ft.attach(mFragment);
	        }
	        if( menu != null ){
		        if( !mTag.equals("generate") ){
		        	menu.clear();
		        }else{
		        	onCreateOptionsMenu(menu);
		        }
	        }
	        ft.commit();
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction DONOTUSE) {
	    	android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	        if (mFragment != null) {
	            // Detach the fragment, because another one is being attached
	            ft.detach(mFragment);
	        }
	        ft.commit();
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        // User selected the already selected tab. Usually do nothing.
	    }
	}
	
}
