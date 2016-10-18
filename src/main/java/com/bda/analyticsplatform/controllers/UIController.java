package com.bda.analyticsplatform.controllers;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bda.analyticsplatform.impl.ServiceControllerImpl;
import com.bda.analyticsplatform.utils.ApplicationUtils;
import com.bda.analyticsplatform.utils.BDAException;

@Controller
public class UIController {
	@RequestMapping("/login")
	public ModelAndView getLoginForm(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		String message = "";
		if (error != null) {
			message = "Incorrect username or password !";
		} else if (logout != null) {
			message = "Logout successful !";
		}
		return new ModelAndView("login", "message", message);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView mainPage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("index");

		return model;

	}
	
	}
