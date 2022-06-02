package net.minedof.web.model.converter;

import net.minedof.web.model.EEnterpriseType;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named
@FacesConverter(value = "enterpriseTypeConverter", managed = true)
public class EnterpriseTypeConverter implements Converter<EEnterpriseType> {

        @Override
        public EEnterpriseType getAsObject(FacesContext context, UIComponent component, String value) {
            if (value != null && value.trim().length() > 0) {
                try {
                    return EEnterpriseType.values()[Integer.parseInt(value)];
                }
                catch (NumberFormatException e) {
                    throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid country."));
                }
            }
            else {
                return null;
            }
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, EEnterpriseType value) {
            if (value != null) {
                return String.valueOf(value.ordinal());
            }
            else {
                return null;
            }
        }
}
