package com.homefix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller()
public class ReviewController {
	

	
	@GetMapping("/reviews/{step}")
	public void viewPage(@PathVariable String step) {
		//return "/board/" + step;
	}
}
