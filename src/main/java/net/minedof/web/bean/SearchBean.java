package net.minedof.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.minedof.web.model.dao.ClientDao;
import net.minedof.web.model.dao.EnterpriseDao;
import net.minedof.web.model.entity.Enterprise;

@Named("searchBean")
@SessionScoped
public class SearchBean implements Serializable {

	@Inject
	private ClientDao clientDao;
	@Inject
	private EnterpriseDao enterpriseDao;

	private List<Enterprise> enterprises;

	private String city;

	@PostConstruct
	public void init() {
		this.enterprises = new ArrayList<Enterprise>();
	}

}
