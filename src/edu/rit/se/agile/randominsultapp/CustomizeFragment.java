package edu.rit.se.agile.randominsultapp;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import edu.rit.se.agile.data.Word;
import edu.rit.se.agile.data.WordType;
import edu.rit.se.agile.data.WordsTemplate;

public class CustomizeFragment extends Fragment {
	
	private Button addWordButton;
	private EditText textField;
	private Spinner spinner;
	private Spinner categorySpinner;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View addView = inflater.inflate(R.layout.activity_add_word, container, false);
		
		addWordButton = (Button) addView.findViewById(R.id.add_word_button);
		textField = (EditText) addView.findViewById(R.id.add_word_text);
		spinner = (Spinner) addView.findViewById(R.id.add_word_spinner);
		categorySpinner = (Spinner) addView.findViewById(R.id.add_word_category_spinner);

		Cursor categoryCursor = RandomInsults.wordDAO.getCategories();
		
		categorySpinner.setAdapter(
				new SimpleCursorAdapter(getActivity(), 
						R.layout.category_list, 
						categoryCursor, 
						new String[]{ WordsTemplate.COLUMN_CATEGORY }, 
						new int[]{ R.id.category_list_entry }, 
						SimpleCursorAdapter.FLAG_AUTO_REQUERY ));
		
		addWordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = textField.getText().toString();
				String spinnerText = spinner.getSelectedItem().toString().toLowerCase();
				String categoryText = ((Cursor) categorySpinner.getSelectedItem()).getString(0);
				String wordType = "";
				
				if (spinnerText.equals("noun")) {
					wordType = WordType.NOUN.val();
				} else if (spinnerText.equals("verb")) {
					wordType = WordType.VERB.val();
				} else if (spinnerText.equals("adjective")) {
					wordType = WordType.ADJECTIVE.val();
				} else if (spinnerText.equals("adverb")) {
					wordType = WordType.ADVERB.val();
				}
				
				Word newWord = RandomInsults.wordDAO.createWord(wordType, text, categoryText);
				Toast.makeText(v.getContext(), 
						newWord.getWord() + " was added.",
						Toast.LENGTH_SHORT).show();
				
				textField.setText("");
			}
		});
		return addView;
	}
}
