package edu.rit.se.agile.data;

public class Word {
	private long id;
	private String type;
	private String word;
	private String category;

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
