package com.example.petclinic;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

	@GetMapping("/showError")
	  public String error() {
	    return "error"; 
	  }
	

	
}
