package by.aliesha.utils.resource.xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

@XmlRootElement(name="Property")
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyFile {
	
	@XmlID
	@XmlAttribute(required=true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	private String id;
	
	@XmlElement(name="Locales", required=false)
	private FileLocales locales;
	
	@XmlElement(name="Path", required=true)
	private String path;
	
	public PropertyFile(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

    public FileLocales getLocales() {
        return locales;
    }

    public void setLocales(FileLocales locales) {
        this.locales = locales;
    }

}
