package net.minedof.web.model;

/**
 * Représente le type d'entreprise.
 */
public enum EEnterpriseType {

    BOULANGERIE("Boulangerie"),
    COIFFEUR("Coiffeur"),
    BARBIER("Barbier"),
    RESTAURANT("Restaurant"),
    ESTHETICIENNE("Esthéticienne");

    private String name;
    EEnterpriseType(String name) {
        this.name = name;
    }

}
