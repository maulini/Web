package net.minedof.web.model.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractDaoGenerique<T> {
	/**
	 * Paramètre de pour les seuils.
	 */
	protected static String parameter = "param";
	// Contexte de persitance
	@PersistenceContext
	private EntityManager em;

	protected PersistenceUnitUtil puu;

	protected EntityManager getEntityManager() {
		return this.em;
	}

	@PostConstruct
	public void init() {
		// si l'entity manager n'est pas null, alors on récupère une instance de
		// PersistenceUnitUtil.
		this.puu = (this.getEntityManager() == null) ? null
				: this.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil();
	}

	/////////////////////////////////// CRUD////////////////////////////////////////////////////////
	public boolean isEmpty() {
		return this.count() == 0;
	}

	// compte le nombre d'entité dans la table
	public Long count() {
		final CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Long> c = qb.createQuery(Long.class);
		c.select(qb.count(c.from(this.getBusinessClass())));
		return this.getEntityManager().createQuery(c).getSingleResult();
	}

	// persistence de l'objet
	public void create(final T t) {
		log.debug(String.format("/////////////sauvegarde de %s /////////////////", t.toString()));
		this.getEntityManager().persist(t);
	}

	// Maj de l'objet
	public T update(final T t) {
		try {
			return this.getEntityManager().merge(t);
		} catch (PersistenceException e) {
			return t;
		}
	}

	// read all
	public List<T> readAll() {
		final CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<T> c = qb.createQuery(this.getBusinessClass());
		final TypedQuery<T> query = this.getEntityManager().createQuery(c);
		return query.getResultList();
	}

	// read
	public T read(final Object id) {
		return this.getEntityManager().find(this.getBusinessClass(), id);
	}

	// delete
	public void delete(final T t) {
		final T attachedEntity = this.getEntityManager().getReference(this.getBusinessClass(),
				this.puu.getIdentifier(t));
		this.getEntityManager().remove(attachedEntity);
	}

	// exist
	public boolean exists(final String parameterName, final Object parameterValue) {
		final CriteriaBuilder qb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Long> c = qb.createQuery(Long.class);
		c.select(qb.count(c.from(this.getBusinessClass())));
		final Root<T> from = c.from(this.getBusinessClass());
		final Predicate restriction = qb.equal(from.get(parameterName), parameterValue);
		c.where(restriction);
		final Long found = this.getEntityManager().createQuery(c).getSingleResult();
		return found != 0;
	}

	//////////////////////////////// FIN
	//////////////////////////////// CRUD////////////////////////////////////////////////////////

	// Introspection : methode qui permet la récuperation de la classe appelant la
	// methode
	@SuppressWarnings("unchecked")
	public Class<T> getBusinessClass() {
		final ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		return (Class<T>) type.getActualTypeArguments()[0];
	}
}
