package com.pw.gwhparser1.loader.appconfig.sample.impl;

import java.io.StringReader;
import java.util.List;

import com.pw.gwhparser1.loader.appconfig.sample.SampleReader;
import com.pw.gwhparser1.parser.model.ElementConfig;
import com.pw.gwhparser1.parser.model.ElementConfigs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenCSVSampleReader implements SampleReader {

    private static final GenCSVSampleReader INSTANCE;

    static {
        try {
            INSTANCE = new GenCSVSampleReader();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private GenCSVSampleReader() {
        super();
        if (INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    public static GenCSVSampleReader getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ElementConfig> readSample(String sample) throws RuntimeException {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ElementConfigs.class);
            Unmarshaller Unmarshaller = jaxbContext.createUnmarshaller();
            ElementConfigs elementConfigs = (ElementConfigs) Unmarshaller.unmarshal(new StringReader(sample));
            return elementConfigs.getElements();
        } catch (Exception e) {
            log.error("Error while parsing sample", e);
            throw new RuntimeException(e);
        }
    }


}
