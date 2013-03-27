package edu.rit.se.agile.randominsultapp;

import edu.rit.se.agile.data.TempleteDAO;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RandomInsults extends Activity {

	private Button generateButton;
	private TextView insultTextField;
	private TempleteDAO templeteDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_insults);
		
		insultTextField = (TextView) findViewById( R.id.insult_display );
		generateButton = (Button) findViewById(R.id.button_generate);
		templeteDAO = new TempleteDAO(this);

		generateButton.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				insultTextField.setText("Some insult.");
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.random_insults, menu);
		return true;
	}

}
