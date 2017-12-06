package pl.scoutbook.model;

public enum PostCategory {
	USER("UserProfile"), 
	GROUP("Group"),
	EVENT("Event"),
	FUNPAGE("Funpage");
	
	private String description;
	
	private PostCategory(String description){
		this.description = description;
	}
	
	@Override
	public String toString(){
		return description;
	}

}
