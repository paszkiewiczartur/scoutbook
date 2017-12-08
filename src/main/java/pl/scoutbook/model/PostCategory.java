package pl.scoutbook.model;

public enum PostCategory {
	USERWALL("UserWall"), 
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
