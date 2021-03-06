package net.minedof.web.bean;

import io.reactivex.Completable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minedof.web.deserializable.JsonUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named("indexBean")
@SessionScoped
@Slf4j
@Getter
@Setter
public class IndexBean implements Serializable {

    private final String API_ADDRESS = "https://api-adresse.data.gouv.fr/search/?q=%s";
    private final String API_POSTAL = "https://api-adresse.data.gouv.fr/search/?q=%s&postcode=%s";

    private String city;
    private String prestation;

    @PostConstruct
    public void init() {
    }

    public List<String> complete(String address) {
        List<String> lst = new ArrayList<>();
        Completable.create(emitter -> {
            try {
                URLConnection url = new URL(String.format(API_ADDRESS, address.replace(' ', '+'))).openConnection();
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

    public void onSelected(AjaxBehaviorEvent event) {
        search();
    }

    public void search() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.getExternalContext().redirect("/Web-1.0-SNAPSHOT/search");
        } catch (IOException e) {
            log.error("An error occur while load searching page", e);
        }
    }

}
