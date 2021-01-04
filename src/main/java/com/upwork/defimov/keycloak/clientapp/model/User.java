package com.upwork.defimov.keycloak.clientapp.model;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLCITextType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
@TypeDefs({ @TypeDef(name = "pgsql_citext", typeClass = PostgreSQLCITextType.class),
		@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class) })
@Immutable
public class User implements Serializable {
	private UUID id;

	@NotNull
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	protected User() {
		// JPA only
	}

	@NotNull
	@OneToMany(mappedBy = "user")
	private Set<Account> accounts;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@Access(AccessType.PROPERTY)
	public UUID getId() {
		return id;
	}

	// JPA Only
	protected void setId(UUID id) {
		this.id = id;
	}
}
