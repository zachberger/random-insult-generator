package edu.rit.se.agile.randominsultapp;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import edu.rit.se.agile.data.Template;
import edu.rit.se.agile.data.TemplateDAO;
import edu.rit.se.agile.data.WordDAO;
import edu.rit.se.agile.data.WordsTemplate;

public class RandomInsults extends GenericActivity {

	private Button generateButton;
	private TextView insultTextField;
	private TemplateDAO templateDAO;
	private WordDAO wordDAO;
	private Spinner categorySpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_insults);
		
		insultTextField = (TextView) findViewById( R.id.insult_display );
		generateButton = (Button) findViewById(R.id.button_generate);
		categorySpinner = (Spinner) findViewById(R.id.category_spinner);

		templateDAO = new TemplateDAO(this);
		wordDAO  = new WordDAO(this);

		templateDAO.open();
		wordDAO.open();
		

		// TODO: Add a cursor adapter from DAO once categories are implemented
		//TODO: zach look at dis
		categorySpinner.setAdapter(
				new SimpleCursorAdapter(this, 
						R.id.category_list, 
						wordDAO.getCategories(), 
						new String[]{ WordsTemplate.COLUMN_CATEGORY }, 
						new int[]{ R.id.category_list }, 
						SimpleCursorAdapter.FLAG_AUTO_REQUERY ));
		
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
	public void onPause() {
		super.onPause();
		wordDAO.close();
		templateDAO.close();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		wordDAO.close();
		templateDAO.close();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		wordDAO.open();
		templateDAO.open();
	}
}
