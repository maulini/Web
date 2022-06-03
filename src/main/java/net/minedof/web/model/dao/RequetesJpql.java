package net.minedof.web.model.dao;

/**
 * Class pour toute les requêtes jpql.
 * @author valentin.maulini
 *
 */
public final class RequetesJpql {
	
	public static final String DERNIERE_CONSOMMATION = "SELECT c FROM Consommation c WHERE c.client = :param ORDER BY c.dateConsommation DESC";
	
	public static final String TOUTE_LES_CONSOMMATION = "SELECT c FROM Consommation c WHERE c.client = :param";
	
	public static final String DERNIER_MOUVEMENT_CAISSE = "SELECT mvt FROM MouvementCaisse mvt ORDER BY mvt.dateMouvement DESC";
	
	public static final String SOLDE_CAISSE = "SELECT SUM(m.montant) FROM MouvementCaisse m ";
	
	public static final String CLIENT_ALPHABETIQUE= "SELECT c FROM Client c ORDER BY c.nom ASC";
	
	public static final String PRODUIT_ACTIFS = "SELECT p FROM Produit p WHERE p.actif = true AND p.quantiteStock > 0";
	
	public static final String CLIENT_ACTIFS = "SELECT c FROM Client c WHERE c.actif = true";
	
	public static final String RECUPERER_PARAMETRE = "SELECT p FROM ParametresAppli p";
	
	public static final String PRODUIT_SEUIL_CRITIQUE = "SELECT p FROM Produit p WHERE p.actif = true AND p.quantiteStock <= :param";
	
	public static final String PRODUIT_SEUIL_ALERTE = "SELECT p FROM Produit p WHERE p.actif = true AND p.quantiteStock <= :param AND p.quantiteStock > :param2";
	
	public static final String LISTE_COURSE = "SELECT p, :param - p.quantiteStock FROM Produit p WHERE p.actif = true AND p.quantiteStock < :param";
	
	public static final String STATISTIQUE_COMMANDE = "SELECT COUNT(commande), FUNC('MONTH', commande.dateConsommation), FUNC('YEAR', commande.dateConsommation) FROM Consommation commande GROUP BY FUNC('MONTH', commande.dateConsommation), FUNC('YEAR', commande.dateConsommation)";
	
	public static final String STATISTIQUE_SOLDE_CAISSE = "SELECT SUM(mc.montant), FUNC('MONTH', mc.dateMouvement), FUNC('YEAR', mc.dateMouvement) FROM MouvementCaisse mc GROUP BY FUNC('MONTH', mc.dateMouvement), FUNC('YEAR', mc.dateMouvement)";
	
	public static final String STATISTIQUE_PRODUIT_CONSOMER = "SELECT p, COUNT(p) * AVG(l.quantite) FROM Consommation c LEFT JOIN c.lstLigneDeConsommation l LEFT JOIN l.produit p WHERE FUNC('MONTH', CURRENT_DATE) = FUNC('MONTH', c.dateConsommation) AND FUNC('YEAR', CURRENT_DATE) = FUNC('YEAR', c.dateConsommation) GROUP BY p.nom, FUNC('MONTH', c.dateConsommation), FUNC('YEAR', c.dateConsommation) ORDER BY COUNT(p) * AVG(l.quantite) DESC";
	
	public static final String CLIENT_SOLDE_ALERT = "SELECT c FROM Client c WHERE c.solde <= :param AND c.actif = true";

	public static final String ACCOUNT_ALREADY_EXIST = "SELECT a FROM Account a WHERE a.mail = :param";
	public static final String FIND_CLIENT_BY_EMAIL = "SELECT c FROM Client c WHERE c.account = (SELECT a.id FROM Account a WHERE a.mail like :param)";

	private RequetesJpql() {
	}
	
}
