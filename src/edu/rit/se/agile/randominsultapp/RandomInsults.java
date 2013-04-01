package edu.rit.se.agile.randominsultapp;

import java.util.List;

import edu.rit.se.agile.data.Template;
import edu.rit.se.agile.data.TemplateDAO;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class RandomInsults extends Activity {

	private Button generateButton;
	private TextView insultTextField;
	private Spinner categorySpinner;
	private TemplateDAO templateDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_insults);
		
		insultTextField = (TextView) findViewById( R.id.insult_display );
		generateButton = (Button) findViewById(R.id.button_generate);
		categorySpinner = (Spinner) findViewById(R.id.category_spinner);

		templateDAO = new TemplateDAO(this);
		templateDAO.open();

		// TODO: Add a cursor adapter from DAO once categories are implemented
		//categorySpinner.setAdapter( );

		generateButton.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				List<Template> temp = templateDAO.getAllTemplates();
				insultTextField.setText("Some insult.");
				if(temp.size() > 0 ) {
					insultTextField.setText(temp.get(0).getTemplate());
				}
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
