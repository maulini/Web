package net.minedof.web.model.dao;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;

import net.minedof.web.model.Address;
import net.minedof.web.model.entity.Client;
import net.minedof.web.model.entity.Enterprise;

@Dependent
public class ClientDao extends AbstractDaoGenerique<Client> implements Serializable {

	/**
	 * Permet d'enregistrer une entreprise préférer au client.
	 * @param client
	 *          Le client à modifier.
	 * @param enterprise
	 *          Entreprise à associer au client.
	 */
	public void setFavoriteEnterprise(Client client, Enterprise enterprise) {
		client.setFavoriteEnterprise(enterprise);
		this.update(client);
	}

	/**
	 * Permet de modifier l'adresse d'un client.
	 * @param client
	 *          Le client à modifier.
	 * @param newAddress
	 *          La nouvelle adresse du client.
	 */
	public void modifyAddress(Client client, Address newAddress) {
		client.setAddress(newAddress);
		this.update(client);
	}

	public Client getClient(String mailSing) {
		TypedQuery<Client> tq = this.getEntityManager().createNamedQuery("client.findClient", Client.class);
		tq.setParameter(parameter, mailSing);
		if (!tq.getResultList().isEmpty()) {
			return tq.getResultList().get(0);
		}
		return null;
	}

	public void updateName(String name, String mailSing) {

	}

	public void updateLastName(String lastName, String mailSing) {

	}

	public void updateLastNameAndName(String lastName, String name, String mailSing) {

	}
}
