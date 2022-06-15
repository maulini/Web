package net.minedof.web.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.primefaces.PrimeFaces;
import org.primefaces.component.card.Card;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.outputlabel.OutputLabel;

import io.reactivex.Completable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minedof.web.deserializable.JsonUtils;
import net.minedof.web.model.Address;
import net.minedof.web.model.EEnterpriseType;
import net.minedof.web.model.dao.AccountDao;
import net.minedof.web.model.dao.AddressDao;
import net.minedof.web.model.dao.ClientDao;
import net.minedof.web.model.dao.EnterpriseDao;
import net.minedof.web.model.entity.Account;
import net.minedof.web.model.entity.Client;
import net.minedof.web.model.entity.Enterprise;
import net.minedof.web.utils.PasswordEncoder;

@Named("loginBean")
@SessionScoped
@Slf4j
@Getter
@Setter
public class LoginBean implements Serializable {

	private final String API_ADDRESS = "https://api-adresse.data.gouv.fr/search/?q=%s";
	private static final String ALREADY_CONNECTED = "alreadyConnected";
	private static final String INSCRIPTION = "Crée mon compte";
	private static final String L_INSCRIPTION = "S'inscrire";
	private static final String CONNEXION = "Connection";
	private static final String LA_CONNEXION = "Se connecter";

	@NotNull
	private String mail;
	@NotNull
	private String password;
	@NotNull
	private String mailSing;
	@NotNull
	private String passwordSing;
	@NotNull
	private String phone;
	private String type;
	@NotNull
	private String enterpriseName;
	@NotNull
	private EEnterpriseType enterpriseType = EEnterpriseType.BARBIER;
	@NotNull
	private Address enterpriseAddress;
	@NotNull
	private String clientName;
	@NotNull
	private String clientLastName;
	private List<EEnterpriseType> enterpriseTypeList;

	@Inject
	private AccountDao accountDao;
	@Inject
	private ClientDao clientDao;
	@Inject
	private EnterpriseDao enterpriseDao;
	@Inject
	private AddressDao addressDao;

	@PostConstruct
	public void init() {
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().containsKey(ALREADY_CONNECTED)) {
			Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get(ALREADY_CONNECTED);
			Account account = this.accountDao.getAccount(cookie.getValue());
			if (account != null && account.getAccountType().equals("Client")) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/client");
				} catch (IOException e) {
					log.error("An error occure while trying to redirect.", e);
				}
			} else if (account != null && account.getAccountType().equals("Enterprise")) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/enterprise");
				} catch (IOException e) {
					log.error("An error occure while trying to redirect.", e);
				}
			}
		}
		this.type = "Client";
		this.enterpriseTypeList = Arrays.asList(EEnterpriseType.values());
	}

	public void connexion() {
		UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent cnxBtn = view.findComponent("login:btnCnx");
		UIComponent insBtn = view.findComponent("singin:cbIns");
		this.switchPanel(view, cnxBtn, insBtn, true, EType.LOGIN, EType.SINGIN);
		if (!cnxBtn.getAttributes().get("value").equals(EType.LOGIN.str1) && this.mail != null && !this.mail.trim().isEmpty() && this.password != null && !this.password.trim().isEmpty()) {
			if (this.accountDao.verifyAccount(this.mail, this.password)) {
				FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(ALREADY_CONNECTED, this.mail, null);
				Account account = this.accountDao.getAccount(this.mail);
				if (account.getAccountType().equals("Client")) {
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("/client");
					} catch (IOException e) {
						log.error("An error ocurre while trying to redirect", e);
					}
				} else {
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("/enterprise");
					} catch (IOException e) {
						log.error("An error ocurre while trying to redirect", e);
					}
				}
			} else {
				FacesContext.getCurrentInstance().addMessage("login:loginGrow", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Saisie incorrect", "Le mot de passe ou l'adresse mail est incorrect"));
			}
		}
	}

	@Transactional
	public void inscription() {
		UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
		UIComponent cnxBtn = view.findComponent("login:btnCnx");
		UIComponent insBtn = view.findComponent("singin:cbIns");
		if (!insBtn.getAttributes().get("value").equals(EType.SINGIN.str1) && this.mailSing != null && !this.mailSing.trim().isEmpty() && this.passwordSing != null && !this.passwordSing.trim().isEmpty() && this.phone != null && !this.phone.trim().isEmpty()) {
			if (!this.emailAlreadyExist()) {
				if (this.type.equals("Client")) {
					Account account = new Account(this.mailSing, PasswordEncoder.generateSecurePassword(this.passwordSing), "Client");
					this.accountDao.create(account);
					Address address = new Address();
					Address addressE = new Address();
					this.addressDao.create(address);
					this.addressDao.create(addressE);
					Enterprise enterprise = new Enterprise();
					enterprise.setAddress(addressE);
					this.enterpriseDao.create(enterprise);
					Client client = new Client();
					client.setPhoneNumber(this.getPhone());
					client.setAccount(account);
					client.setAddress(address);
					client.setFavoriteEnterprise(enterprise);
					this.clientDao.create(client);
					this.showClientPanel(view);
					FacesContext.getCurrentInstance().
					addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le compte client à bien été crée !", null));
				} else {
					Account account = new Account(this.mailSing, PasswordEncoder.generateSecurePassword(this.passwordSing), "Enterprise");
					this.accountDao.create(account);
					Enterprise enterprise = new Enterprise();
					enterprise.setAccount(account);
					this.enterpriseDao.create(enterprise);
					this.showEnterprisePanel(view);
					FacesContext.getCurrentInstance().
					addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le compte entreprise à bien été crée !", null));
				}
				PrimeFaces.current().ajax().update(view.findComponent("singin:singinGrowl"));
			} else {
				FacesContext.getCurrentInstance().
				addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cette adresse est déjà utilisé", String.format("Veuillez vous connecter avec l'adresse %s s'il vous plait", this.mailSing)));
				PrimeFaces.current().ajax().update(view.findComponent("singin:singinGrowlDetails"));
			}
		}
		this.switchPanel(view, insBtn, cnxBtn, false, EType.SINGIN, EType.LOGIN);
	}

	private boolean emailAlreadyExist() {
		return this.accountDao.alreadyExist(this.mailSing);
	}

	private void showEnterprisePanel(UIViewRoot view) {
		this.hidePrimaryPanel(view);
		Card enterprise = ((Card)view.findComponent("enterprise:enterpriseDiv"));
		enterprise.setStyleClass(enterprise.getStyleClass() + " active");
		PrimeFaces.current().ajax().update(enterprise);
	}

	private void hidePrimaryPanel(UIViewRoot view) {
		Card singinPanel = ((Card)view.findComponent("singin:singinDiv"));
		Card loginPanel = ((Card)view.findComponent("login:loginDiv"));
		Card backgroundPanel = ((Card)view.findComponent("login:backgroundPanel"));
		CommandButton cnxBtn = (CommandButton) view.findComponent("login:btnCnx");
		CommandButton insBtn = (CommandButton) view.findComponent("singin:cbIns");
		OutputLabel alreadyUseLabel = (OutputLabel) view.findComponent("login:alreadyUseLabel");
		OutputLabel newOnWebSitePanel = (OutputLabel) view.findComponent("singin:newOnWebSitePanel");
		OutputLabel label = (OutputLabel) view.findComponent("or:orLbl");
		if (singinPanel.getStyleClass().contains("active")) {
			singinPanel.setStyleClass(singinPanel.getStyleClass().replaceAll(" active", ""));
		} else if (loginPanel.getStyleClass().contains("active")) {
			loginPanel.setStyleClass(singinPanel.getStyleClass().replaceAll(" active", ""));
		}
		cnxBtn.setStyle("display: none;");
		insBtn.setStyle("display: none;");
		label.setStyle("display: none;");
		backgroundPanel.setStyle("display: none;");
		alreadyUseLabel.setStyle("display: none;");
		newOnWebSitePanel.setStyle("display: none;");
		PrimeFaces.current().ajax().update(singinPanel, loginPanel, cnxBtn, insBtn, backgroundPanel, label, alreadyUseLabel, newOnWebSitePanel);
	}

	private void showClientPanel(UIViewRoot view) {
		this.hidePrimaryPanel(view);
		Card client = ((Card)view.findComponent("client:clientDiv"));
		client.setStyleClass(client.getStyleClass() + " active");
		PrimeFaces.current().ajax().update(client);
	}

	private void switchPanel(UIViewRoot view, UIComponent firstBtn, UIComponent secondButton, boolean singHide, EType fistETypeButton, EType secondETypeButton) {
		Card singinPanel = ((Card)view.findComponent("singin:singinDiv"));
		Card loginPanel = ((Card)view.findComponent("login:loginDiv"));
		if (firstBtn.getAttributes().get("value").equals(fistETypeButton.str1)) {
			if (singHide) {
				singinPanel.setStyleClass(singinPanel.getStyleClass().replaceAll(" active", ""));
				loginPanel.setStyleClass(loginPanel.getStyleClass() + " active");
			}else {
				singinPanel.setStyleClass(singinPanel.getStyleClass() + " active");
				loginPanel.setStyleClass(loginPanel.getStyleClass().replaceAll(" active", ""));
			}
			secondButton.getAttributes().put("value", secondETypeButton.str1);
		}
		if (!firstBtn.getAttributes().get("value").equals(fistETypeButton.str2)) {
			firstBtn.getAttributes().put("value", fistETypeButton.str2);
		} else if (!secondButton.getAttributes().get("value").equals(secondETypeButton.str1)) {
			firstBtn.getAttributes().put("value", fistETypeButton.str1);
		}
		PrimeFaces.current().ajax().update(secondButton, firstBtn, singinPanel, loginPanel);
	}

	public List<String> enterpriseCompletAddress(String query) {
		List<String> lst = new ArrayList<>();
		Completable.create(emitter -> {
			try {
				URLConnection url = new URL(String.format(this.API_ADDRESS, query.replace(' ', '+'))).openConnection();
				lst.addAll(JsonUtils.getCityAddress(new BufferedReader(new InputStreamReader(url.getInputStream()))
						.lines().collect(Collectors.joining("\n"))));
				emitter.onComplete();
			} catch (IOException e) {
				log.error("An error occur while getting city.", e);
				emitter.onError(e);
			}
		}).blockingAwait();
		return lst;
	}

	public List<EEnterpriseType> enterpriseCompletType(String query) {
		return this.enterpriseTypeList.stream().filter(enterprise -> enterprise.name().contains(query)).collect(Collectors.toList());
	}

	@Transactional
	public void validate() {
		if (this.type.equals("Client")) {
			if (this.clientName != null && !this.clientName.trim().isEmpty() && this.clientLastName != null && !this.clientLastName.trim().isEmpty()) {
				Client client = this.clientDao.getClient(this.mailSing);
				if (client != null) {
					client.setFirstName(this.clientName);
					client.setLastName(this.clientLastName);
					this.clientDao.update(client);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, String.format("Bienvenue %s", client.getFirstName()), null));
					PrimeFaces.current().ajax().update("client:clientSaved");
					FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(ALREADY_CONNECTED, this.mailSing, null);
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("/client");
					} catch (IOException e) {
						log.error("An error ocurre while trying to redirect", e);
					}
				}
			}
		}else {
			if (this.enterpriseName != null && !this.enterpriseName.trim().isEmpty() && this.enterpriseType != null && this.enterpriseAddress != null) {
				Enterprise enterprise = this.enterpriseDao.getEnterprise(this.mailSing);
				if (enterprise != null) {
					this.addressDao.create(this.enterpriseAddress);
					enterprise.setName(this.enterpriseName);
					enterprise.setAddress(this.enterpriseAddress);
					enterprise.setEnterpriseType(this.enterpriseType.getId());
					this.enterpriseDao.update(enterprise);
					FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(ALREADY_CONNECTED, this.mailSing, null);
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("/enterprise");
					} catch (IOException e) {
						log.error("An error ocurre while trying to redirect", e);
					}
				}
			}
		}
	}

	public void deconexion() {
		FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().remove(ALREADY_CONNECTED);
	}

	enum EType {
		SINGIN(L_INSCRIPTION, INSCRIPTION),
		LOGIN(LA_CONNEXION, CONNEXION);

		@Getter
		private String str1;
		@Getter
		private String str2;

		EType(String str1, String str2) {
			this.str1 = str1;
			this.str2 = str2;
		}
	}

}
