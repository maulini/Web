package net.minedof.web.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.*;

import javafx.scene.image.Image;
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
@NamedQuery(name="enterprise.getEnterpriseNotByCityAndType", query= RequetesJpql.FIND_ENTERPRISE_NOT_BY_CITY_TYPE)
@NamedQuery(name="enterprise.getEnterpriseByCityAndType", query= RequetesJpql.FIND_ENTERPRISE_BY_CITY_TYPE)
public class Enterprise extends AbstractEntity implements Serializable {

	/**
	 * Nom de l'entreprise.
	 */
	String name;

	/**
	 * Adresse de l'entreprise.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	Address address;

	/**
	 * Type d'entreprise.
	 */
	int enterpriseType;

	/**
	 * Compte de l'entreprise.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	Account account;

	/**
	 * Tous les clients ayant pris rendez-vous avec l'entreprise.
	 */
	@OneToMany
	List<Client> client;

	/**
	 * Image desciptive du produit
	 */
	@OneToMany(cascade = CascadeType.ALL)
	List<Photo> image;

	/**
	 * numéro de version.
	 */
	@Version
	@Column(name = "optlock", columnDefinition = "integer NOT NULL DEFAULT 0")
	Long version = 0L;
}
