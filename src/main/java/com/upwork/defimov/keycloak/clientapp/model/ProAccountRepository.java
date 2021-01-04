package com.upwork.defimov.keycloak.clientapp.model;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface ProAccountRepository extends CrudRepository<ProAccount, UUID> {

	public List<ProAccount> findByAccount_User(User user);
}
