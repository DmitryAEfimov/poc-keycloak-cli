package com.upwork.defimov.keycloak.clientapp.model;

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

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "pros")
@Access(value = AccessType.FIELD)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Immutable
public class ProAccount {
	private UUID id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Column(name = "settings", nullable = false)
	@Type(type = "jsonb")
	private JsonSettings settings;

	protected ProAccount() {
		// JPA only
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Access(javax.persistence.AccessType.PROPERTY)
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

	public JsonSettings getSettings() {
		return settings;
	}

	public Account getAccount() {
		return account;
	}
}
