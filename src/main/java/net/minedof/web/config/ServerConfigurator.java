package net.minedof.web.config;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.minedof.web.model.dao.SaltDao;
import net.minedof.web.model.entity.Salt;
import net.minedof.web.utils.PasswordEncoder;

@Singleton
@Startup
@Slf4j
public class ServerConfigurator {

	@Inject
	private SaltDao saltDao;

	@PostConstruct
	public void init() {
		String salt = this.saltDao.getSalt();
		if (salt != null) {
			PasswordEncoder.salt = salt;
			log.info("Récupération du salt");
		} else {
			PasswordEncoder.getSaltValue(30);
			this.saltDao.create(new Salt(0, PasswordEncoder.salt));
			log.info("Création du salt");
		}
	}

}
