package net.minedof.web.model.dao;

import net.minedof.web.model.entity.Account;

import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import java.io.Serializable;

@Dependent
public class AccountDao extends AbstractDaoGenerique<Account> implements Serializable {
    /**
     * Paramètre de pour les seuils.
     */
    private static String parameter = "param";

    public boolean verifyAccount(String mail, String password) {
        return false;
    }

    public boolean alreadyExist(String mailSing) {
        TypedQuery<Account> tq = getEntityManager().createNamedQuery("account.verifyAlreadyEmailExist", Account.class);
        tq.setParameter(parameter, mailSing);
        return !tq.getResultList().isEmpty();
    }
}
