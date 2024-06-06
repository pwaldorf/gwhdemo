package com.pw.gwhparser1.loader.appconfig.sample;


import com.pw.gwhparser1.loader.appconfig.sample.impl.GenCSVSampleReader;
import com.pw.gwhparser1.loader.appconfig.sample.impl.GenFIXSampleReader;
import com.pw.gwhparser1.loader.appconfig.sample.impl.GenJSONSampleReader;
import com.pw.gwhparser1.loader.appconfig.sample.impl.GenXMLSampleReader;
import com.pw.gwhparser1.parser.config.GenParserConfig;
import com.pw.gwhparser1.parser.enums.ParserFormat;


public class SampleReaderFactory {

    private static final SampleReaderFactory INSTANCE;

    static {
        try {
            INSTANCE = new SampleReaderFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SampleReaderFactory() {
        super();
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to get the instance of this class");
        }
    }

    public static SampleReaderFactory getInstance() {
        return INSTANCE;
    }

    public SampleReader getReader(GenParserConfig config) {
        if (config == null) {
            throw new RuntimeException("config is null");
        }
        return getReader(config.getFormat());
    }

    public SampleReader getReader(ParserFormat format) {
        if (format == null) {
            throw new RuntimeException("format is null");
        }
        switch (format) {
            case XML:
                return GenXMLSampleReader.getInstance();
            case JSON:
                return GenJSONSampleReader.getInstance();
            case FIXEDLENGTH:
                return GenFIXSampleReader.getInstance();
            case CSV:
                return GenCSVSampleReader.getInstance();
            default:
                break;
        }
        throw new IllegalArgumentException("Unsupported format: " + format);
    }
}
