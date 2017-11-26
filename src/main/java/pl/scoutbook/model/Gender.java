package pl.scoutbook.model;

public enum Gender {
	MALE("mężczyzna"), FEMALE("kobieta");
	
	private String description;
	
	private Gender(String description){
		this.description = description;
	}
	
	@Override
	public String toString(){
		return description;
	}
	
}
