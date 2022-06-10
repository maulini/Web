package net.minedof.web.model.dao;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;

import net.minedof.web.model.entity.Salt;

@Dependent
public class SaltDao extends AbstractDaoGenerique<Salt> implements Serializable {

	public String getSalt() {
		TypedQuery<String> tq = this.getEntityManager().createNamedQuery("salt.getSalt", String.class);
		if (!tq.getResultList().isEmpty()) {
			return tq.getSingleResult();
		}
		return null;
	}


}
