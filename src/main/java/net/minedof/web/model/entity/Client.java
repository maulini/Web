package net.minedof.web.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minedof.web.model.Address;

import javax.enterprise.context.Dependent;
import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Dependent
@Entity
@Table(name="CLIENT")
//@NamedQuery(name="client.triAlphabetique", query=RequetesJpql.CLIENT_ALPHABETIQUE)
public class Client extends AbstractEntity implements Serializable {

    /**
     * Prénom du client.
     */
    String firstName;
    /**
     * Nom de famille du client.
     */
    String lastName;
    /**
     * Numéro de téléphone du client.
     */
    String phoneNumber;

    /**
     * Lieu de résidence du client.
     */
    @OneToOne
    Address address;

    /**
     * Entreprise préférée du client.
     */
    @ManyToOne
    Enterprise favoriteEnterprise;

    @OneToOne
    Account account;

    /**
     * numéro de version.
     */
    @Version
    @Column(name = "optlock", columnDefinition = "integer NOT NULL DEFAULT 0")
    Long version = 0L;

    public Client(String firstName, String lastName, String phoneNumber, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}
