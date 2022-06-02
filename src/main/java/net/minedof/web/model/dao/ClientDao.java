package net.minedof.web.model.dao;

import net.minedof.web.model.Address;
import net.minedof.web.model.entity.Client;
import net.minedof.web.model.entity.Enterprise;

import javax.enterprise.context.Dependent;
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

}
