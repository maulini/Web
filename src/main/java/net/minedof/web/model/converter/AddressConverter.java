package net.minedof.web.model.converter;

import net.minedof.web.model.Address;
import net.minedof.web.utils.AddressUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named
@FacesConverter(value = "addressConverter", managed = true)
public class AddressConverter implements Converter<Address> {

    @Override
    public Address getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return AddressUtils.parseAddress(value);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid address."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Address value) {
        if (value != null) {
            return value.getCompleteAddress();
        } else {
            return null;
        }
    }
}
