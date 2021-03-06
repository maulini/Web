package net.minedof.web.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minedof.web.model.entity.AbstractEntity;

import javax.enterprise.context.Dependent;
import javax.persistence.*;
import java.io.Serializable;

//lombok
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)

//JPA
@Entity
@Table(name="ADDRESS")

//CDI
@Dependent
public class Address extends AbstractEntity implements Serializable {

    /**
     * Nom de la ville.
     */
    String city;
    /**
     * Adresse du client ou de l'entreprise.
     */
    String address;
    /**
     * Code postal de la ville.
     */
    int cityZip;

    /**
     * numéro de version.
     */
    @Version
    @Column(name = "optlock", columnDefinition = "integer NOT NULL DEFAULT 0")
    Long version = 0L;

}
