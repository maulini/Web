package net.minedof.web.utils;

import io.reactivex.Completable;
import lombok.extern.slf4j.Slf4j;
import net.minedof.web.deserializable.JsonUtils;
import net.minedof.web.model.Address;
import net.minedof.web.model.Location;
import org.omnifaces.util.Json;
import org.primefaces.model.map.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public final class AddressUtils {

    private static final String API_ADDRESS = "https://api-adresse.data.gouv.fr/search/?q=%s";

    private AddressUtils(){}

    public static Address parseAddress(String addressToParse) {
        Address address = null;
        try {
            URLConnection url = new URL(String.format(API_ADDRESS, addressToParse.replace(' ', '+'))).openConnection();
            String jsonValue = new BufferedReader(new InputStreamReader(url.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));
            List<String> name = JsonUtils.getName(jsonValue);
            List<String> city = JsonUtils.getCity(jsonValue);
            List<String> cityCode = JsonUtils.getCityCode(jsonValue);
            List<Location> location = JsonUtils.getLocation(jsonValue);
            if (!name.isEmpty() && !city.isEmpty() && !cityCode.isEmpty() && !location.isEmpty()) {
                address = new Address(city.get(0), name.get(0), Integer.parseInt(cityCode.get(0)), String.format("%s, %s %s", name.get(0), city.get(0), cityCode.get(0)), location.get(0), 0L);
            }
        } catch (IOException e) {
            log.error("An error occur while getting city.", e);
        }
        return address;
    }

    public static List<Address> parseAddresses(String addressToParse) {
        Set<Address> address = new HashSet<>();
        UUID uuid = UUID.randomUUID();
        try {
            URLConnection url = new URL(String.format(API_ADDRESS, addressToParse.replace(' ', '+'))).openConnection();
            String jsonValue = new BufferedReader(new InputStreamReader(url.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));
            List<String> name = JsonUtils.getName(jsonValue);
            List<String> city = JsonUtils.getCity(jsonValue);
            List<String> cityCode = JsonUtils.getCityCode(jsonValue);
            List<Location> location = JsonUtils.getLocation(jsonValue);
            if (!name.isEmpty() && !city.isEmpty() && !cityCode.isEmpty() && !location.isEmpty() && name.size() == city.size() && city.size() == cityCode.size() && location.size() == cityCode.size()) {
                for (int i = 0; i < city.size(); i++) {
                    Address addressQuery = new Address(city.get(i), name.get(i), Integer.parseInt(cityCode.get(i)), String.format("%s, %s %s", name.get(i), city.get(i), cityCode.get(i)), location.get(i), 0L);
                    addressQuery.setId(uuid);
                    address.add(addressQuery);

                }
            }
        } catch (IOException e) {
            log.error("An error occur while getting city.", e);
        }
        return new ArrayList<>(address);
    }

    /**
     * Permet de savoir si l'adresse du client est dans la limite de l'adresse d'une coordonée.
     * @param enterpriseAddress
     *          Adresse de l'entreprise.
     * @param clientAddress
     *          Adresse du client.
     * @param limit
     *          Diamètre du cercle exprimé en mètre.
     * @return
     */
    public static boolean isInBound(Address enterpriseAddress, Address clientAddress, double limit) {
        double distance = GeoUtils.distance(enterpriseAddress.getLonLat().getLat(), clientAddress.getLonLat().getLat(), enterpriseAddress.getLonLat().getLon(), clientAddress.getLonLat().getLon());
        return distance <= limit / 1000;
    }
}
