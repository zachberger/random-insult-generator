package edu.rit.se.agile.data;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {
	
	private long id;
	private String template;
	private String category;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getTemplate() {
		return template;
	}
	
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String fillTemplate(WordDAO wordDAO) {
		String insult = template;
		
		Random rand = new Random();
		
		Pattern adjPattern  = Pattern.compile(WordType.ADJECTIVE.val());
		Pattern advPattern  = Pattern.compile(WordType.ADVERB.val());
		Pattern nounPattern = Pattern.compile(WordType.NOUN.val());
		Pattern verbPattern = Pattern.compile(WordType.VERB.val());
		
		List<Word> adjWords = wordDAO.getAllWords(WordType.ADJECTIVE.val());
		List<Word> advWords = wordDAO.getAllWords(WordType.ADVERB.val());
		List<Word> nounWords = wordDAO.getAllWords(WordType.NOUN.val());
		List<Word> verbWords = wordDAO.getAllWords(WordType.VERB.val());
		
		Collections.shuffle(adjWords);
		Collections.shuffle(advWords);
		Collections.shuffle(nounWords);
		Collections.shuffle(verbWords);
		
		
		String[] insultSplit = insult.split("\\s+");
		String toReturn = "";
		int count = 0;
		while(count < insultSplit.length) {
			if(insultSplit[count].contains(WordType.ADJECTIVE.val())) {
				insultSplit[count] = insultSplit[count].replace(WordType.ADJECTIVE.val(), adjWords.get(0).getWord());
			} else if (insultSplit[count].contains(WordType.ADVERB.val())) {
				insultSplit[count] = insultSplit[count].replace(WordType.ADVERB.val(), advWords.get(0).getWord());
			} else if (insultSplit[count].contains(WordType.NOUN.val())) {
				insultSplit[count] = insultSplit[count].replace(WordType.NOUN.val(), nounWords.get(0).getWord());
			} else if (insultSplit[count].contains(WordType.VERB.val())) {
				insultSplit[count] = insultSplit[count].replace(WordType.VERB.val(), verbWords.get(0).getWord());
			} 
			toReturn +=  " " + insultSplit[count]; 
			count++;
		}
		
		
		
		return toReturn;
		
	}
	/**
	 * Returns how many adjective placeholders are in the template.
	 */
	public int adjCount() {
		Pattern p = Pattern.compile(WordType.ADJECTIVE.val());
	    Matcher m = p.matcher(template);
	    int count = 0;
	    while (m.find()){
	    	count +=1;
	    }
		return count;
	}

	/**
	 * Returns how many adverb placeholders are in the template.
	 */
	public int advCount() {
		Pattern p = Pattern.compile(WordType.ADVERB.val());
	    Matcher m = p.matcher(template);
	    int count = 0;
	    while (m.find()){
	    	count +=1;
	    }
		return count;
	}

	/**
	 * Returns how many noun placeholders are in the template.
	 */
	public int nounCount() {
		Pattern p = Pattern.compile(WordType.NOUN.val());
	    Matcher m = p.matcher(template);
	    int count = 0;
	    while (m.find()){
	    	count +=1;
	    }
		return count;
	}

	/**
	 * Returns how many verb placeholders are in the template.
	 */
	public int verbCount() {
		Pattern p = Pattern.compile(WordType.VERB.val());
	    Matcher m = p.matcher(template);
	    int count = 0;
	    while (m.find()){
	    	count +=1;
	    }
		return count;
	}

	@Override
	public String toString() {
		return "Template [id=" + id + ", template=" + template + ", category="
				+ category + "]";
	}
	
}
