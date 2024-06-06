package com.pw.gwhparser1.loader.appconfig.sample.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.pw.gwhparser1.loader.appconfig.sample.SampleReader;
import com.pw.gwhparser1.loader.appconfig.sample.SampleUtil;
import com.pw.gwhparser1.parser.config.GenParserConfig;
import com.pw.gwhparser1.parser.constants.ParserConstants;
import com.pw.gwhparser1.parser.impl.GenJSONParser;
import com.pw.gwhparser1.parser.model.Entity;
import com.pw.gwhparser1.parser.model.EntityFactory;
import com.pw.gwhparser1.parser.model.PathMapping;
import com.pw.gwhparser1.parser.model.SimpleEntityFactory;


public class GenJSONSampleReader  implements SampleReader {

    private static final GenJSONSampleReader INSTANCE;

    static {
        try {
            INSTANCE = new GenJSONSampleReader();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private GenJSONSampleReader() {
        super();
        if (INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    public static GenJSONSampleReader getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Entry<String, List<PathMapping>>> readSample(String sample) throws RuntimeException {
        GenParserConfig config = new GenParserConfig();
        config.setRootEntityName(ParserConstants.ROOT);
        config.setCharacterSet(ParserConstants.SAMPLE_CHARSET_NAME);
        Parser sampleParser = new Parser(null, config, SimpleEntityFactory.getInstance());
        sampleParser.parse(sample);
        return sampleParser.getMappings();
    }

    private class Parser extends GenJSONParser {

        GenParserConfig sampleConfig;

        public Parser(String appKey, GenParserConfig config, EntityFactory entityFactory) {
            super(appKey, config, entityFactory);
            sampleConfig = new GenParserConfig();
        }

        public Iterator<Entry<String, List<PathMapping>>> getMappings() {
            return sampleConfig.getMappings();
        }

        @Override
        protected void setValue(Object valueObj) {
            if (valueObj == null || !(valueObj instanceof String)) {
                return;
            }
            String xmlValue = ((String) valueObj).trim();
            SampleUtil.addPathMapping(sampleConfig, getTagPathString(), xmlValue);
        }

        @Override
        protected void postProcessing(Entity rooEntity) {
            return;
        }
    }

}
