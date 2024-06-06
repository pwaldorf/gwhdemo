package com.pw.gwhparser1.parser;

import com.pw.gwhparser1.parser.config.GenParserConfig;
import com.pw.gwhparser1.parser.enums.ParserFormat;
import com.pw.gwhparser1.parser.impl.GenCSVParser;
import com.pw.gwhparser1.parser.impl.GenFIXParser;
import com.pw.gwhparser1.parser.impl.GenJSONParser;
import com.pw.gwhparser1.parser.impl.GenXMLParser;
import com.pw.gwhparser1.parser.model.EntityFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParserFactory {

    private static final ParserFactory instance;

    static {
        try {
            instance = new ParserFactory();
        } catch (Exception e) {
            String errorMsg = "ParserFactory initialization failed";
            log.error(errorMsg, e);
            throw new ExceptionInInitializerError(errorMsg);
        }
    }

    private ParserFactory() {
        super();
        if (instance != null) {
            throw new IllegalStateException("ParserFactory instance already created");
        }
    }

    public static ParserFactory getInstance() {
        return instance;
    }

    public Parser getParser(String appKey, GenParserConfig config, EntityFactory entityFactory) {
        if (config == null || entityFactory == null) {
            throw new IllegalArgumentException("config and entityFactory must not be null");
        }
        ParserFormat format = config.getFormat();
        if (format == null) {
            throw new IllegalArgumentException("format must not be null");
        }
        switch (format) {
            case XML:
                return new GenXMLParser(appKey, config, entityFactory);
            case JSON:
                return new GenJSONParser(appKey, config, entityFactory);
            case FIXEDLENGTH:
                return new GenFIXParser(appKey, config, entityFactory);
            case CSV:
                return new GenCSVParser(appKey, config, entityFactory);
            default:
                break;
        }
        throw new IllegalArgumentException("format is not supported: " + format);
    }
}
