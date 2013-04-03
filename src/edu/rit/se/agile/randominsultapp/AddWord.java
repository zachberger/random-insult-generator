package edu.rit.se.agile.randominsultapp;

import java.util.List;

import edu.rit.se.agile.data.Template;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AddWord extends GenericActivity {
	private Button addWordButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_word);
		
		addWordButton = (Button) findViewById(R.id.add_word_button);
		
		addWordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "Do a thing.", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
