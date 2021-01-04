package com.upwork.defimov.keycloak.clientapp.controller;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.upwork.defimov.keycloak.clientapp.dto.RoleProperties;
import com.upwork.defimov.keycloak.clientapp.model.AccountType;
import com.upwork.defimov.keycloak.clientapp.service.RolesLookupService;
import com.upwork.defimov.keycloak.clientapp.service.exception.AccountTypeUnknownException;
import com.upwork.defimov.keycloak.clientapp.service.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CheckAuthController {
	@Autowired
	private RolesLookupService roles;

	@GetMapping("/poc/access/{accountType}")
	public String accessToProfile(HttpServletRequest request, Model model, Principal principal,
			@PathVariable(name = "accountType") AccountType accountType)
			throws AccountTypeUnknownException, UserNotFoundException {

		List<RoleProperties> roleProperties = roles.findUserRoles(principal.getName(), accountType);
		model.addAttribute("roleDetails", roleProperties);
		return "roleDetails";
	}

	@GetMapping(value = { "/" })
	public String initialRedirect() {
		return "redirect:/poc";
	}

	@GetMapping(value = "/poc")
	public String index(Model model, Principal principal) {
		KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) principal;

		List<AccountType> allowedRoles = Stream.of(AccountType.values())
				.filter(value -> token.getAccount().getRoles().contains(value.name()))
				.filter(value -> value != AccountType.regular).sorted(Comparator.comparing(AccountType::name))
				.collect(Collectors.toList());

		KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) token
				.getPrincipal();
		model.addAttribute("keycloakPrincipal", keycloakPrincipal);
		model.addAttribute("userRoles", allowedRoles);

		return "index";
	}

	@PostMapping(value = "/logout")
	public String logout(HttpServletRequest request) throws Exception {
		request.logout();
		return "redirect:/poc";
	}
}
