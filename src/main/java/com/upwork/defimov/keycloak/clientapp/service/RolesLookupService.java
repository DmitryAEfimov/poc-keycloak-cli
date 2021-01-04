package com.upwork.defimov.keycloak.clientapp.service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.upwork.defimov.keycloak.clientapp.dto.RoleProperties;
import com.upwork.defimov.keycloak.clientapp.model.AccountType;
import com.upwork.defimov.keycloak.clientapp.model.ProAccountRepository;
import com.upwork.defimov.keycloak.clientapp.model.SpaceAccountRepository;
import com.upwork.defimov.keycloak.clientapp.model.User;
import com.upwork.defimov.keycloak.clientapp.model.UserRepository;
import com.upwork.defimov.keycloak.clientapp.service.exception.AccountTypeUnknownException;
import com.upwork.defimov.keycloak.clientapp.service.exception.UserNotFoundException;

@Service
public class RolesLookupService {
	@Autowired
	private UserRepository users;
	@Autowired
	private ProAccountRepository proAccounts;
	@Autowired
	private SpaceAccountRepository spaceAccounts;
	@Autowired
	private MessageSource messageSource;

	public List<RoleProperties> findUserRoles(String username, AccountType accountType)
			throws AccountTypeUnknownException, UserNotFoundException {
		User user = findUser(username);
		Stream<RoleProperties> roles;
		switch (accountType) {
		case space:
			roles = spaceAccounts.findByAccount_User(user).stream().map(RoleProperties::new);
			break;
		case pro:
			roles = proAccounts.findByAccount_User(user).stream().map(RoleProperties::new);
			break;
		default:
			throw new AccountTypeUnknownException(
					messageSource.getMessage("unsupportedAccountType", new Object[] { accountType.name() }, Locale.US));
		}

		return roles.sorted(Comparator.comparing(RoleProperties::getAccountType).thenComparing(RoleProperties::getName))
				.collect(Collectors.toList());
	}

	private User findUser(String username) throws UserNotFoundException {
		return users.findByUsername(username).orElseThrow(() -> new UserNotFoundException(
				messageSource.getMessage("userEmailAlreadyExists", new Object[] { username }, Locale.US)));
	}
}
