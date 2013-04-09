package edu.rit.se.agile.data;

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
