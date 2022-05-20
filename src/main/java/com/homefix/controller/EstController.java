package com.homefix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estimation")
public class EstController {
	@GetMapping("/{step}")
	public void viewPage(@PathVariable String step) {
		//return "/board/" + step;
	}
}
