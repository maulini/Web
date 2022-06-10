package net.minedof.web.model.dao;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;

import net.minedof.web.model.entity.Account;
import net.minedof.web.utils.PasswordEncoder;

/**
 * Représente les actions en base de donnée pour les comptes.
 * @author valentin
 *
 */
@Dependent
public class AccountDao extends AbstractDaoGenerique<Account> implements Serializable {

	/**
	 * Permet de vérifier si le compte est bien renseigné.
	 * @param mail
	 * 			Le mail pour se connecter
	 * @param password
	 * 			Le mot de passe du compte
	 * @return
	 * 			<code>true</code> Si l'adresse mail et le mot de passe correspond.
	 */
	public boolean verifyAccount(String mail, String password) {
		Account account = this.getAccount(mail);
		if (account != null) {
			return PasswordEncoder.verifyUserPassword(password, account.getPassword());
		}
		return false;
	}

	/**
	 * Récupère un compte en fonction de l'email.
	 * @param mail
	 * 			Le mail du compte
	 * @return
	 * 			{@link Account} si trouvé sinon <code>null</code>
	 */
	public Account getAccount(String mail) {
		TypedQuery<Account> tq = this.getEntityManager().createNamedQuery("account.getAccountByMail", Account.class);
		tq.setParameter(parameter, mail);
		try {
			return tq.getSingleResult();
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Vérifie si le compte existe déjà.
	 * @param mailSing
	 * 			Le mail du compte.
	 * @return
	 * 			<code>true</code> si un compte avec l'adresse mail existe déjà sinon <code>false</code>
	 */
	public boolean alreadyExist(String mailSing) {
		TypedQuery<Account> tq = this.getEntityManager().createNamedQuery("account.verifyAlreadyEmailExist", Account.class);
		tq.setParameter(parameter, mailSing);
		return !tq.getResultList().isEmpty();
	}

}
