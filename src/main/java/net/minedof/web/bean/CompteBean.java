package net.minedof.web.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minedof.web.model.dao.AccountDao;
import net.minedof.web.model.entity.Account;

/**
 * Représente la page pour la gestion de compte.
 * @author valentin
 *
 */
@Named("compteBean")
@SessionScoped
@Slf4j
@Getter
@Setter
public class CompteBean implements Serializable {

	@Inject
	private AccountDao accountDao;

	@PostConstruct
	public void init() {
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().containsKey("alreadyConnected")) {
			Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get("alreadyConnected");
			Account account = this.accountDao.getAccount(cookie.getValue());
			if (account.getAccountType().equals("Client")) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/Web/client");
				} catch (IOException e) {
					log.error("An error occure while trying to redirect.", e);
				}
			} else if (account.getAccountType().equals("Enterprise")) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/Web/enterprise");
				} catch (IOException e) {
					log.error("An error occure while trying to redirect.", e);
				}
			}
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/Web/login");
			} catch (IOException e) {
				log.error("An error occure while trying to redirect.", e);
			}
		}
	}

}
