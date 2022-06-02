package net.minedof.web.model.dao;

import net.minedof.web.model.entity.Account;

import javax.enterprise.context.Dependent;
import java.io.Serializable;

@Dependent
public class AccountDao extends AbstractDaoGenerique<Account> implements Serializable {

    public void verifyAccount(String mail, String password) {

    }
}
