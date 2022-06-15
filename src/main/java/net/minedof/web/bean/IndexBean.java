package net.minedof.web.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

import io.reactivex.Completable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minedof.web.deserializable.JsonUtils;
import net.minedof.web.model.Address;
import net.minedof.web.model.EEnterpriseType;
import net.minedof.web.utils.AddressUtils;

@Named("indexBean")
@SessionScoped
@Slf4j
@Getter
@Setter
public class IndexBean implements Serializable {

	private final String API_ADDRESS = "https://api-adresse.data.gouv.fr/search/?q=%s";
	private final String API_POSTAL = "https://api-adresse.data.gouv.fr/search/?q=%s&postcode=%s";

	private Address city;
	private EEnterpriseType prestation;

	@PostConstruct
	public void init() {
	}

	public List<Address> complete(String address) {
		return AddressUtils.parseAddresses(address);
	}

	public List<EEnterpriseType> completePrestation(String query) {
		return Stream.of(EEnterpriseType.values()).filter(e -> e.name.contains(query)).collect(Collectors.toList());
	}

	public void search() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			context.getExternalContext().redirect("/search");
		} catch (IOException e) {
			log.error("An error occur while load searching page", e);
		}
	}

}
