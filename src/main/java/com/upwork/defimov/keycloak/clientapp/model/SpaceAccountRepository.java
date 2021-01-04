package com.upwork.defimov.keycloak.clientapp.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface SpaceAccountRepository extends CrudRepository<SpaceAccount, UUID> {
	public List<SpaceAccount> findByAccount_User(User user);
}
