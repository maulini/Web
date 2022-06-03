package net.minedof.web.model.dao;

import net.minedof.web.model.Address;
import net.minedof.web.model.entity.Account;
import net.minedof.web.model.entity.Client;
import net.minedof.web.model.entity.Enterprise;

import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import java.io.Serializable;

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
        update(client);
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
        update(client);
    }

    public Client getClient(String mailSing) {
        TypedQuery<Client> tq = getEntityManager().createNamedQuery("client.findClient", Client.class);
        tq.setParameter(parameter, mailSing);
        return tq.getSingleResult();
    }

    public void updateName(String name, String mailSing) {

    }

    public void updateLastName(String lastName, String mailSing) {

    }

    public void updateLastNameAndName(String lastName, String name, String mailSing) {

    }
}
