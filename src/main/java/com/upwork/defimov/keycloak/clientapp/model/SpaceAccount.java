package com.upwork.defimov.keycloak.clientapp.model;

import org.hibernate.annotations.Immutable;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "spaces")
@Access(value = AccessType.FIELD)
@Immutable
public class SpaceAccount {
	private UUID id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Column(name = "slug", nullable = false)
	private String slug;

	@Column(name = "description")
	private String description;

	protected SpaceAccount() {
		// JPA only
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@Access(value = AccessType.PROPERTY)
	public UUID getId() {
		return id;
	}

	// JPA Only
	protected void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getSlug() {
		return slug;
	}

	public String getDescription() {
		return description;
	}

	public Account getAccount() {
		return account;
	}
}
