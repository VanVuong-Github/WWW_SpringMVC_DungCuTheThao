package com.se.dungcuthethao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class HomeController {

	@GetMapping(value = { "/" })
	public String home() {
		return "index";
	}
}
