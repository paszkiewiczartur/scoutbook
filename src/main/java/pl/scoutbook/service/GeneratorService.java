package pl.scoutbook.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import pl.scoutbook.entities.UserProfile;
import pl.scoutbook.model.ConversationUserDTO;

@Service
public class GeneratorService {
    private static final String fileName = "zasady.txt";
    private static List<String> principles = readPrinciples();
    
    private static List<String> readPrinciples(){
    	List<String> principles = null;
        File file = new File(fileName);
        boolean fileExists = file.exists();
        if(fileExists){
            try (
                    FileReader fileReader = new FileReader(fileName);
                    BufferedReader reader = new BufferedReader(fileReader);
                ) {
            		principles = new LinkedList<>();
                    String nextLine = null;
                    while ((nextLine = reader.readLine()) != null) {
                        principles.add(nextLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
            	System.out.println("File " + fileName + " couldn't be found.");
            }

    	return principles;
    }
    
    public String generatePrinciple(){
    	Random rand = new Random();
    	return principles.get(rand.nextInt(principles.size()));
    }    
    
    public ConversationUserDTO getGeneratorUserProfile(){
    	ConversationUserDTO user = new ConversationUserDTO();
    	user.setFirstname("Generator");
    	user.setLastname("zasad programowania");
    	user.setProfileImage("http://www.gold-binary-robot.com/images/robot-img.png");
    	user.setId(Long.valueOf(0));
    	return user;
    }
}
