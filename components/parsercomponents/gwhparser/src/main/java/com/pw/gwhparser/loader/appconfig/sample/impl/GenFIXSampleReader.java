package com.pw.gwhparser.loader.appconfig.sample.impl;

import java.io.StringReader;
import java.util.List;

import com.pw.gwhparser.loader.appconfig.sample.SampleReader;
import com.pw.gwhparser.parser.model.ElementConfig;
import com.pw.gwhparser.parser.model.ElementConfigs;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenFIXSampleReader implements SampleReader {

    private static final GenFIXSampleReader INSTANCE;

    static {
        try {
            INSTANCE = new GenFIXSampleReader();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private GenFIXSampleReader() {
        super();
        if (INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    public static GenFIXSampleReader getInstance() {
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
