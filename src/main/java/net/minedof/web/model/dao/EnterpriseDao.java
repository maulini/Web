package net.minedof.web.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;

import net.minedof.web.model.Address;
import net.minedof.web.model.EEnterpriseType;
import net.minedof.web.model.entity.Enterprise;

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
		this.update(enterprise);
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
		this.update(enterprise);
	}

	/**
	 * Permet de récupérer une entreprise en fonction de son adresse mail.
	 * @param mailSing
	 * 			l'adresse mail de l'entreprise.
	 * @return
	 * 		{@link Enterprise} trouvée s'il y en a une.
	 */
	public Enterprise getEnterprise(String mailSing) {
		TypedQuery<Enterprise> tq = this.getEntityManager().createNamedQuery("enterprise.getEnterpriseByMail", Enterprise.class);
		tq.setParameter(parameter, mailSing);
		return tq.getSingleResult();
	}

	/**
	 * Permet de récupérer les entreprises d'une même ville.
	 * @param cityName
	 * 			le nom de la ville de l'entreprise.
	 * @return
	 * 		{@link List}<{@link Enterprise}> trouvées.
	 */
	public List<Enterprise> getEnterpriseByCity(String cityName) {
		TypedQuery<Enterprise> tq = this.getEntityManager().createNamedQuery("enterprise.getEnterpriseByCity", Enterprise.class);
		tq.setParameter(parameter, cityName);
		return tq.getResultList();
	}

}
