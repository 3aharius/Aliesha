package by.aliesha.resource.xml.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "", propOrder = { "locales" })
@XmlRootElement(name = "Locales")
@XmlAccessorType(XmlAccessType.FIELD)
public class FileLocales {
    
    @XmlElement(name="Locale", required=true)
    private List<String> locales;
    
    public List<String> getLocales() {
        return locales == null ? new ArrayList<String>() : locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }

}
