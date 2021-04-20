package com.study.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class CustomErrorController implements ErrorController{

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
	
	@RequestMapping(value = "/error", method= {RequestMethod.GET, RequestMethod.POST}) 
	public String handleError(HttpServletRequest request, Model model) { 
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		int statusCode = 400;
		if(status != null){ 
			statusCode = Integer.valueOf(status.toString());  
		}
		HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
		model.addAttribute("code", statusCode);
		model.addAttribute("msg", statusCode + " " + httpStatus.getReasonPhrase());
		return "error"; 
	}

}
