package edu.rit.se.agile.data;

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
		
		List<Word> adjWords = wordDAO.getAllWords();
		List<Word> advWords = wordDAO.getAllWords(WordType.ADVERB.toString());
//		List<Word> nounWords = wordDAO.getAllWords();
//		List<Word> verbWords = wordDAO.getAllWords();
		if(adjWords.size() <= 0) {
			Word fake = new Word();
			fake.setWord("FAKE_WORD");
			adjWords.add(fake);
		}
//		List<Word> advWords = adjWords;
		List<Word> nounWords = adjWords;
		List<Word> verbWords = adjWords;
		
		
		//Replace all Adjective placeholders in the template with an appropriate word.
		for(int i=0; i<this.adjCount(); i++) {
			int randWord = rand.nextInt(adjWords.size()-1);
			insult.replaceFirst(adjPattern.pattern(), adjWords.get(randWord).getWord());
		}

		//Replace all Adverb placeholders in the template with an appropriate word.
		for(int i=0; i<this.adjCount(); i++) {
			int randWord = rand.nextInt(adjWords.size()-1);
			insult.replaceFirst(adjPattern.pattern(), adjWords.get(randWord).getWord());
		}

		//Replace all Noun placeholders in the template with an appropriate word.
		for(int i=0; i<this.adjCount(); i++) {
			int randWord = rand.nextInt(adjWords.size()-1);
			insult.replaceFirst(adjPattern.pattern(), adjWords.get(randWord).getWord());
		}

		//Replace all Verb placeholders in the template with an appropriate word.
		for(int i=0; i<this.adjCount(); i++) {
			int randWord = rand.nextInt(adjWords.size()-1);
			insult.replaceFirst(adjPattern.pattern(), adjWords.get(randWord).getWord());
		}
		
		return insult;
		
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
