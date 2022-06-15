package net.minedof.web.config;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.minedof.web.model.Address;
import net.minedof.web.model.EEnterpriseType;
import net.minedof.web.model.Location;
import net.minedof.web.model.dao.AddressDao;
import net.minedof.web.model.dao.EnterpriseDao;
import net.minedof.web.model.dao.PhotoDao;
import net.minedof.web.model.dao.SaltDao;
import net.minedof.web.model.entity.Enterprise;
import net.minedof.web.model.entity.Photo;
import net.minedof.web.model.entity.Salt;
import net.minedof.web.utils.AddressUtils;
import net.minedof.web.utils.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
