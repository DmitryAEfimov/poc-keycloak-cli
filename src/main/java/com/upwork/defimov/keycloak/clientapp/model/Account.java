package com.upwork.defimov.keycloak.clientapp.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "accounts")
@Access(value = AccessType.FIELD)
@Immutable
public class Account {

	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "active", nullable = false)
	private boolean active;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_type", nullable = false)
	private AccountType accountType;

	@OneToMany(
			mappedBy = "account",
			orphanRemoval = true,
			cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH })
	private Set<SpaceAccount> spaces;

	@OneToMany(
			mappedBy = "account",
			orphanRemoval = true,
			cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH })
	private Set<ProAccount> pros;

	protected Account() {
		// JPA only
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Access(AccessType.PROPERTY)
	public UUID getId() {
		return id;
	}

	// JPA Only
	protected void setId(UUID id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public AccountType getAccountType() {
		return accountType;
	}
}
