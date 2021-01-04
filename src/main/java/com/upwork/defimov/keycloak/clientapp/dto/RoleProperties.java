package com.upwork.defimov.keycloak.clientapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upwork.defimov.keycloak.clientapp.model.AccountType;
import com.upwork.defimov.keycloak.clientapp.model.ProAccount;
import com.upwork.defimov.keycloak.clientapp.model.SpaceAccount;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Slf4j
public class RoleProperties {
	private AccountType accountType;
	@JsonProperty(required = true)
	private String name;
	private String slug;
	private String description;
	private String settings;
	private boolean active;

	public RoleProperties(ProAccount pro) {
		ObjectMapper objectMapper = new ObjectMapper();
		this.name = pro.getName();
		try {
			this.settings = objectMapper.writeValueAsString(pro.getSettings());
		} catch (JsonProcessingException ex) {
			log.error("can't parse user settins", ex);
		}

		this.accountType = pro.getAccount().getAccountType();
		this.active = pro.getAccount().isActive();
	}

	public RoleProperties(SpaceAccount space) {
		this.name = space.getName();
		this.slug = space.getSlug();
		this.description = space.getDescription();

		this.accountType = space.getAccount().getAccountType();
		this.active = space.getAccount().isActive();
	}
}
