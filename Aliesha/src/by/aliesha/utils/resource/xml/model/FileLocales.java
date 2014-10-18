package by.aliesha.utils.resource.xml.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="Locales")
@XmlAccessorType(XmlAccessType.FIELD)
public class FileLocales {
    
    @XmlElement(name="Locale", required=true)
    private List<String> locales;
    
    public FileLocales(){}

    public List<String> getLocales() {
        return locales;
    }

    public void setLocales(List<String> locales) {
        this.locales = locales;
    }

}
