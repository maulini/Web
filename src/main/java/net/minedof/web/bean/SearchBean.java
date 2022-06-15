package net.minedof.web.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minedof.web.model.dao.ClientDao;
import net.minedof.web.model.dao.EnterpriseDao;
import net.minedof.web.model.entity.Enterprise;
import net.minedof.web.utils.AddressUtils;
import org.primefaces.model.ResponsiveOption;

@Named("searchBean")
@ViewScoped
@Slf4j
@Getter
@Setter
public class SearchBean implements Serializable {

    @Inject
    private ClientDao clientDao;
    @Inject
    private EnterpriseDao enterpriseDao;
    private List<Enterprise> enterprises;
    private List<ResponsiveOption> responsiveOptions;
    @Inject
    private IndexBean indexBean;

    @PostConstruct
    public void init() {
        this.enterprises = new ArrayList<Enterprise>();
        responsiveOptions = new ArrayList<>();
        responsiveOptions.add(new ResponsiveOption("560px", 1));
        if (indexBean == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/home");
            } catch (IOException e) {
                log.error("An error occur while trying to redirect.", e);
            }
        }
        if (indexBean.getPrestation() != null) {
            enterprises.addAll(enterpriseDao.getEnterpriseByCityAndType(indexBean.getCity().getCity(), indexBean.getPrestation()));
            enterprises.addAll(enterpriseDao.getEnterpriseNotByCityAndType(indexBean.getCity().getCity(),
                    indexBean.getPrestation()).stream().filter(e -> AddressUtils.isInBound(e.getAddress(), indexBean.getCity(), 40_000))
                    .collect(Collectors.toList()));
        } else {
            enterprises.addAll(enterpriseDao.getEnterpriseByCity(indexBean.getCity().getCity()));
            enterprises.addAll(enterpriseDao.getEnterpriseNotByCity(indexBean.getCity().getCity()).stream().filter(e -> AddressUtils.isInBound(e.getAddress(), indexBean.getCity(), 40_000))
                    .collect(Collectors.toList()));
        }
    }

    public void changeActiveIndex(Enterprise enterprise) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }

}
