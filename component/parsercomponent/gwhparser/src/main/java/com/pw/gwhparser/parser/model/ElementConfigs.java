package com.pw.gwhparser.parser.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Fixedlength and CSV
@XmlRootElement(name = "ElementConfigs")
public class ElementConfigs {

    List<ElementConfig> elements;

    public List<ElementConfig> getElements() {
        return elements;
    }

    @XmlElement(name = "ElementConfig")
    public void setElements(List<ElementConfig> elements) {
        this.elements = elements;
    }

    public void add(ElementConfig elementConfig) {

        if (this.elements == null) {
            this.elements = new ArrayList<>();
        }
        this.elements.add(elementConfig);
	}

}
