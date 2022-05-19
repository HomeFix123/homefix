package com.homefix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estimation2")
public class EstController2 {
	@GetMapping("/{step}")
	public void viewPage(@PathVariable String step) {
		//return "/board/" + step;
	}
}
