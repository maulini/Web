package net.minedof.web.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minedof.web.model.Address;
import net.minedof.web.model.EEnterpriseType;

import javax.enterprise.context.Dependent;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * Représente le modèle d'une entreprise.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Dependent
@Entity
@Table(name="ENTERPRISE")
public class Enterprise extends AbstractEntity implements Serializable {

    /**
     * Nom de l'entreprise.
     */
    String name;
    /**
     * Adresse de l'entreprise.
     */
    @OneToOne
    Address address;
    /**
     * Type d'entreprise.
     */
    EEnterpriseType enterpriseType;
    @OneToOne
    /**
     * Compte de l'entreprise.
     */
    Account account;
    /**
     * Tous les clients ayant pris rendez-vous avec l'entreprise.
     */
    @ManyToMany
    List<Client> client;

    /**
     * numéro de version.
     */
    @Version
    @Column(name = "optlock", columnDefinition = "integer NOT NULL DEFAULT 0")
    Long version = 0L;
}
