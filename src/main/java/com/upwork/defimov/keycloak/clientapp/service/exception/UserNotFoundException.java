package com.upwork.defimov.keycloak.clientapp.service.exception;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message) {
		super(message);
	}
}
