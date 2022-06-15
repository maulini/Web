package net.minedof.web.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minedof.web.model.entity.AbstractEntity;

import javax.enterprise.context.Dependent;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Représente les coordonnées, Lon Lat.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Dependent
@Table(name = "LOCATION")
public class Location extends AbstractEntity {

    double lon;
    double lat;

}
