package net.minedof.web.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.enterprise.context.Dependent;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Dependent
@Entity
@Table(name="PHOTO")
public class Photo extends AbstractEntity implements Serializable {

    String name;
    byte[] image;
    String alt;

}
