package net.minedof.web.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minedof.web.model.Address;
import net.minedof.web.model.EEnterpriseType;
import net.minedof.web.model.dao.RequetesJpql;


/**
 * Représente le modèle d'une entreprise.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Dependent
@Entity
@Table(name="ENTERPRISE")
@NamedQuery(name="enterprise.getEnterpriseByMail", query= RequetesJpql.FIND_ENTERPRISE_BY_CLIENT)
@NamedQuery(name="enterprise.getEnterpriseByCity", query= RequetesJpql.FIND_ENTERPRISE_BY_CITY)
@NamedQuery(name="enterprise.getEnterpriseNotByCity", query= RequetesJpql.FIND_ENTERPRISE_NOT_BY_CITY)
public class Enterprise extends AbstractEntity implements Serializable {

	/**
	 * Nom de l'entreprise.
	 */
	String name;
	/**
	 * Adresse de l'entreprise.
	 */
	@OneToOne
	Address address;
	/**
	 * Type d'entreprise.
	 */
	EEnterpriseType enterpriseType;
	@OneToOne
	/**
	 * Compte de l'entreprise.
	 */
	Account account;
	/**
	 * Tous les clients ayant pris rendez-vous avec l'entreprise.
	 */
	@ManyToMany
	List<Client> client;

	/**
	 * numéro de version.
	 */
	@Version
	@Column(name = "optlock", columnDefinition = "integer NOT NULL DEFAULT 0")
	Long version = 0L;
}
