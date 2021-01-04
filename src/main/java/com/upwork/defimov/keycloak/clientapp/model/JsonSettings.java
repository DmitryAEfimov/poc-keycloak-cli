package com.upwork.defimov.keycloak.clientapp.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
public class JsonSettings implements Serializable {
	private String settings;

	@JsonCreator
	public JsonSettings(String settings) {
		this.settings = settings;
	}
}
