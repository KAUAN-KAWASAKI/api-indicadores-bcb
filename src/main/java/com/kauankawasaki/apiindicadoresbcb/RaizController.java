package com.kauankawasaki.apiindicadoresbcb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RaizController {

	@GetMapping("/")
	public String raiz() {
		return "redirect:/swagger-ui.html";
	}

}
