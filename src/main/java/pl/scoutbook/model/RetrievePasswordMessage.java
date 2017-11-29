package pl.scoutbook.model;

public class RetrievePasswordMessage {
	private String email;
	
	public RetrievePasswordMessage(){}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
}
