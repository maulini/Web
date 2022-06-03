package net.minedof.web.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minedof.web.model.Address;
import net.minedof.web.model.EEnterpriseType;
import net.minedof.web.model.dao.AccountDao;
import net.minedof.web.model.dao.AddressDao;
import net.minedof.web.model.dao.ClientDao;
import net.minedof.web.model.dao.EnterpriseDao;
import net.minedof.web.model.entity.Account;
import net.minedof.web.model.entity.Client;
import net.minedof.web.model.entity.Enterprise;
import org.primefaces.PrimeFaces;
import org.primefaces.component.card.Card;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.panel.Panel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Named("loginBean")
@SessionScoped
@Slf4j
@Getter
@Setter
public class LoginBean implements Serializable {

    private static final String INSCRIPTION = "Crée mon compte";
    private static final String L_INSCRIPTION = "S'inscrire";
    private static final String CONNEXION = "Connection";
    private static final String LA_CONNEXION = "Se connecter";

    private String mail;
    private String password;
    private String mailSing;
    private String passwordSing;
    private String phone;
    private String type;
    private String enterpriseName;
    private EEnterpriseType enterpriseType;
    private String enterpriseAddress;
    private String clientName;
    private String clientLastName;

    private boolean alreadyConnected;

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
        type = "Client";
    }

    public void connexion() {
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent cnxBtn = view.findComponent("login:btnCnx");
        UIComponent insBtn = view.findComponent("singin:cbIns");
        switchPanel(view, cnxBtn, insBtn, true, EType.LOGIN, EType.SINGIN);
        if (!cnxBtn.getAttributes().get("value").equals(EType.LOGIN.str1) && mail != null && !mail.trim().isEmpty() && password != null && !password.trim().isEmpty()) {
            alreadyConnected = accountDao.verifyAccount(mail, password);
        }
    }

    @Transactional
    public void inscription() {
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent cnxBtn = view.findComponent("login:btnCnx");
        UIComponent insBtn = view.findComponent("singin:cbIns");
        switchPanel(view, insBtn, cnxBtn, false, EType.SINGIN, EType.LOGIN);
        if (!insBtn.getAttributes().get("value").equals(EType.SINGIN.str1) && mailSing != null && !mailSing.trim().isEmpty() && passwordSing != null && !passwordSing.trim().isEmpty() && phone != null && !phone.trim().isEmpty()) {
            if (!emailAlreadyExist()) {
                if (type.equals("Client")) {
                    Account account = new Account(mailSing, passwordSing);
                    accountDao.create(account);
                    Address address = new Address();
                    Address addressE = new Address();
                    addressDao.create(address);
                    addressDao.create(addressE);
                    Enterprise enterprise = new Enterprise();
                    enterprise.setAddress(addressE);
                    enterpriseDao.create(enterprise);
                    Client client = new Client();
                    client.setPhoneNumber(getPhone());
                    client.setAccount(account);
                    client.setAddress(address);
                    client.setFavoriteEnterprise(enterprise);
                    clientDao.create(client);
                    showClientPanel(view);
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le compte client à bien été crée !", null));
                } else {
                    Account account = new Account(mailSing, passwordSing);
                    accountDao.create(account);
                    Enterprise enterprise = new Enterprise();
                    enterprise.setAccount(account);
                    enterpriseDao.create(enterprise);
                    showEnterprisePanel(view);
                    FacesContext.getCurrentInstance().
                            addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Le compte entreprise à bien été crée !", null));
                }
                PrimeFaces.current().ajax().update(view.findComponent("singin:singinGrowl"));
            } else {
                FacesContext.getCurrentInstance().
                        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cette adresse est déjà utilisé", String.format("Veuillez vous connecter avec l'adresse %s s'il vous plait", mailSing)));
                PrimeFaces.current().ajax().update(view.findComponent("singin:singinGrowlDetails"));
            }
        }
    }

    private boolean emailAlreadyExist() {
        return accountDao.alreadyExist(mailSing);
    }

    private void showEnterprisePanel(UIViewRoot view) {
        hidePrimaryPanel(view);
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
        hidePrimaryPanel(view);
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

    public List<String> enterpriseCompletAddress() {
        return new ArrayList<>();
    }

    public List<EEnterpriseType> enterpriseCompletType() {
        return new ArrayList<>();
    }

    public void validate() {
        if (type.equals("Client")) {
            if (clientName != null && !clientName.trim().isEmpty() && clientLastName != null && !clientLastName.trim().isEmpty()) {
                Client client = clientDao.getClient(mailSing);
                if (client != null) {
                    client.setFirstName(clientName);
                    client.setLastName(clientLastName);
                    clientDao.update(client);
                }
            }
        }else {

        }
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
