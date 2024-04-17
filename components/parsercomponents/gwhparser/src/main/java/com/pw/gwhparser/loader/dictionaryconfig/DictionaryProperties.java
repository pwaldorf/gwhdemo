package com.pw.gwhparser.loader.dictionaryconfig;

import java.util.ArrayList;
import java.util.List;

import com.pw.gwhparser.parser.model.Element;

public class DictionaryProperties {

    private String dictionaryName;
    private List<Element> elements;

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public void addElement(Element element) {

        if(element != null) {
            if(elements == null) {
                elements = new ArrayList<>();
            }
            elements.add(element);
        }
    }

    public void addDummyElement() {

        setDictionaryName("dummy");

        Element element = new Element();
        element.setName("dummy");
        element.setDescription("Dummy Element");
        addElement(element);
    }


}
