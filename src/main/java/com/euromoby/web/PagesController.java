package com.euromoby.web;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {

    @RequestMapping("/")
    public String index(Model model) {
    	model.addAttribute("today", new Date());
        return "index";
    }	
	
}
