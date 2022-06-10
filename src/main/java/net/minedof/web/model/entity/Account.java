package net.minedof.web.model.entity;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minedof.web.model.dao.RequetesJpql;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Dependent
@Entity
@Table(name="ACCOUNT")
@NamedQuery(name = "account.verifyAlreadyEmailExist", query = RequetesJpql.ACCOUNT_ALREADY_EXIST)
@NamedQuery(name = "account.getAccountByMail", query = RequetesJpql.FIND_ACCOUNT_BY_ADDRESS)
public class Account extends AbstractEntity implements Serializable {

	String mail;
	String password;
	String accountType;

}
