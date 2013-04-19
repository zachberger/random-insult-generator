package edu.rit.se.agile.randominsultapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class RandomInsults extends GenericActivity {

	Menu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//	    actionBar.setDisplayShowHomeEnabled(false);
//	    actionBar.setDisplayShowTitleEnabled(false);

	    Tab tab = actionBar.newTab()
	            .setText("Generate Insults")
	            .setTabListener(new TabListener<GenerateInsultsFragment>(
	                    this, "generate", GenerateInsultsFragment.class));
	    actionBar.addTab(tab);
	    
	    tab = actionBar.newTab()
	            .setText("Favorites")
	            .setTabListener(new TabListener<ViewFavorites>(
	                    this, "favorites", ViewFavorites.class));
	    actionBar.addTab(tab);
	    
	    tab = actionBar.newTab()
	            .setText("Customize")
	            .setTabListener(new TabListener<AddWord>(
	                    this, "customize", AddWord.class));
	    
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

	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
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
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        if (mFragment != null) {
	            // Detach the fragment, because another one is being attached
	            ft.detach(mFragment);
	        }
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        // User selected the already selected tab. Usually do nothing.
	    }
	}
	
}
