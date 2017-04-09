package com.basut.townmanager.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cheat")
public class CheatController {

	@RequestMapping
	public String cheatBasic() {
		//for later cheating of states
		return "redirect:";
	}

	
}
