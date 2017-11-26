package pl.scoutbook.model;

import java.util.LinkedList;
import java.util.List;

public class Errors {
	private List<String> errors;

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Errors(){
		errors = new LinkedList<>();
	}
	
}
