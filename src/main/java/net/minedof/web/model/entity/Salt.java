package net.minedof.web.model.entity;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Dependent
@Entity
@Table(name="SALT")
@NamedQuery(name = "salt.getSalt", query = "SELECT s.salt FROM Salt s")
public class Salt implements Serializable {

	@Id
	@Column(columnDefinition = "int auto_increment")
	int id;

	@Column(columnDefinition = "text(256)")
	String salt;

}
