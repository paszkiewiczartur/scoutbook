package pl.scoutbook.config;

import java.util.Set;

import org.springframework.stereotype.Component;

import pl.scoutbook.model.Errors;

@Component
public class ResponseRegisterValidation {
	public ResponseRegisterValidation(){
	}
	
	public Errors makeRegisterResponse(Set<String> errors) {
		Errors response = new Errors();
		for(String error: errors){
			response.getErrors().add(takeErrorCodeName(error));
		}
		return response;
	}

	private String takeErrorCodeName(String input){
		String tab[] = input.split(";");
		String code = tab[1].trim();
		tab = code.substring(7, code.length()-1).split(",");
		code = tab[tab.length-1];
		return code;
	}

}
