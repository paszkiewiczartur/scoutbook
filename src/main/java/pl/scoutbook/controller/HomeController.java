package pl.scoutbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "index.html";
    }   
    @RequestMapping("/secret")
    public String administrator() {
    	System.out.println("Przekierowuję do admina");
        return "admin.html";
    }
}