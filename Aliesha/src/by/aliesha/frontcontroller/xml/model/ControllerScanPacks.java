package by.aliesha.frontcontroller.xml.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "packages" })
@XmlRootElement(name = "ControllerScanPacks")
public class ControllerScanPacks {

    @XmlElement(name = "Package", required = true)
    protected List<String> packages;

    public List<String> getPackages() {
        if (packages == null) {
            packages = new ArrayList<String>();
        }
        return this.packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

}
