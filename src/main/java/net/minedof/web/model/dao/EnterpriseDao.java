package net.minedof.web.model.dao;

import net.minedof.web.model.Address;
import net.minedof.web.model.EEnterpriseType;
import net.minedof.web.model.entity.Client;
import net.minedof.web.model.entity.Enterprise;

import javax.enterprise.context.Dependent;
import java.io.Serializable;

@Dependent
public class EnterpriseDao extends AbstractDaoGenerique<Enterprise> implements Serializable {

    /**
     * Permet d'enregistrer une entreprise préférer au client.
     * @param enterprise
     *          L'entreprise à modifier.
     * @param enterpriseType
     *          Le type d'entreprise à associer au client.
     */
    public void setFavoriteEnterprise(Enterprise enterprise, EEnterpriseType enterpriseType) {
        enterprise.setEnterpriseType(enterpriseType);
        update(enterprise);
    }

    /**
     * Permet de modifier l'adresse d'un entreprise.
     * @param enterprise
     *          L'enterprise à modifier.
     * @param newAddress
     *          La nouvelle adresse de l'entreprise.
     */
    public void modifyAddress(Enterprise enterprise, Address newAddress) {
        enterprise.setAddress(newAddress);
        update(enterprise);
    }

}
