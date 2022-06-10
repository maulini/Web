package net.minedof.web.model.dao;

/**
 * Class pour toute les requêtes jpql.
 * @author valentin.maulini
 *
 */
public final class RequetesJpql {

	public static final String ACCOUNT_ALREADY_EXIST = "SELECT a FROM Account a WHERE a.mail = :param";

	public static final String FIND_CLIENT_BY_EMAIL = "SELECT c FROM Client c WHERE c.account = (SELECT a.id FROM Account a WHERE a.mail like :param)";

	public static final String FIND_ENTERPRISE_BY_CLIENT = "SELECT e FROM Enterprise e WHERE e.account = (SELECT a.id FROM Account a WHERE a.mail like :param)";

	public static final String FIND_ENTERPRISE_BY_CITY = "SELECT e FROM Enterprise e WHERE e.address = (SELECT a.id FROM Address a WHERE a.city like :param)";

	public static final String FIND_ENTERPRISE_NOT_BY_CITY = "SELECT e FROM Enterprise e WHERE e.address = (SELECT a.id FROM Address a WHERE a.city not like :param)";

	public static final String FIND_ACCOUNT_BY_ADDRESS = "SELECT a FROM Account a WHERE a.mail = :param";

	private RequetesJpql() {
	}

}
