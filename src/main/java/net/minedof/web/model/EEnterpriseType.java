package net.minedof.web.model;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Représente le type d'entreprise.
 */
public enum EEnterpriseType {

	BOULANGERIE("Boulangerie", 0),
	COIFFEUR("Coiffeur", 1),
	BARBIER("Barbier", 2),
	RESTAURANT("Restaurant", 3),
	ESTHETICIENNE("Esthéticienne", 4);

	public String name;
	public int id;

	EEnterpriseType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return this.name;
	}
	public int getId() {
		return this.id;
	}

	public static EEnterpriseType findEnterpriseType(String name) {
		return Stream.of(values()).filter(e -> e.name().equals(name)).findFirst().orElse(null);
	}

	public static EEnterpriseType findEnterpriseTypeById(int id) {
		return Stream.of(values()).filter(e -> e.id == id).findFirst().orElse(null);
	}

}
