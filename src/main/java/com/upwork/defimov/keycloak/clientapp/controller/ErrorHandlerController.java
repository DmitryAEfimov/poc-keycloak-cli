package com.upwork.defimov.keycloak.clientapp.controller;

import com.upwork.defimov.keycloak.clientapp.service.exception.AccountTypeUnknownException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.upwork.defimov.keycloak.clientapp.service.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ErrorHandlerController {

	@GetMapping(value = "/403")
	public String accessDenied(Model model) {
		return "error/403";
	}

	@ExceptionHandler(value = { AccountTypeUnknownException.class, UserNotFoundException.class })
	private ModelAndView userNotFoundException(ModelAndView model, Exception ex) {

		model.addObject("error", ex.getMessage());
		model.setViewName("notFound");

		return model;
	}
}
