package pl.scoutbook.model;

public enum Gender {
	FEMALE("kobieta"), MALE("mężczyzna");
	
	private String description;
	
	private Gender(String description){
		this.description = description;
	}
	
	@Override
	public String toString(){
		return description;
	}
	
}
