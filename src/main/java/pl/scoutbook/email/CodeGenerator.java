package pl.scoutbook.email;

import java.util.Random;

public class CodeGenerator {
	public static String generateCode(){
		int wordLength = 30;
		char signs[] = new char[62];
		char word[] = new char[wordLength];
	    String randomString = "";
	    Random random = new Random();
		for(int i = 0; i < 10; i++){
			signs[i] = (char)(i+'0');
		}
		for(int i = 10; i < 36; i++){
			signs[i] = (char)(i-10+'A');
		}
		for(int i = 36; i < 62; i++){
			signs[i] = (char)(i-36+'a');
		}
		for(int i = 0; i < wordLength; i++ ){
			word[i] += signs[random.nextInt(signs.length)];
		}
	    randomString = new String(word);
	    return randomString;
	}
}
