package com.pw.dataformats.gatewaydataformat.dataformat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spi.DataFormatName;
import org.apache.camel.support.service.ServiceSupport;
import org.apache.camel.util.IOHelper;
import org.apache.camel.util.ObjectHelper;
import org.apache.camel.util.StringHelper;

import com.pw.gwhparser.loader.ConfigLoaderStrategy;
import com.pw.gwhparser.loader.appconfig.XmlAppConfigLoader;
import com.pw.gwhparser.loader.dictionaryconfig.DictionaryProperties;
import com.pw.gwhparser.loader.dictionaryconfig.PropertiesDictionaryLoader;
import com.pw.gwhparser.loader.parserconfig.ParserConfigProperties;
import com.pw.gwhparser.loader.parserconfig.PropertiesParserConfigLoader;
import com.pw.gwhparser.parser.Parser;
import com.pw.gwhparser.parser.ParserFactory;
import com.pw.gwhparser.parser.config.GenParserConfig;
import com.pw.gwhparser.parser.enums.ParserFormat;
import com.pw.gwhparser.parser.enums.ValueType;
import com.pw.gwhparser.parser.model.Dictionary;
import com.pw.gwhparser.parser.model.Element;
import com.pw.gwhparser.parser.model.Entity;
import com.pw.gwhparser.parser.model.EntityFactory;
import com.pw.gwhparser.parser.model.SimpleEntityFactory;


public class GatewayDataFormat extends ServiceSupport implements DataFormat, DataFormatName, CamelContextAware {


    private ParserConfigProperties parserConfigProperties;
    private DictionaryProperties dictionaryProperties;
    private String appConfig = null;


    private transient CamelContext camelContext;
    private ConfigLoaderStrategy<Dictionary, DictionaryProperties> dictionaryLoader = null;
    private ConfigLoaderStrategy<GenParserConfig, ParserConfigProperties> parserConfigLoader = null;
    private ConfigLoaderStrategy<GenParserConfig, String> appConfigLoader = null;

    public GatewayDataFormat() {
        parserConfigProperties = new ParserConfigProperties();
        dictionaryProperties = new DictionaryProperties();
    }


    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'marshal'");
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {

        BufferedReader streamReader = IOHelper.buffered(new InputStreamReader(stream, getCharacterSet()));
        //String data = exchange.getContext().getTypeConverter().mandatoryConvertTo(String.class, exchange, streamReader);

        StringHelper.notEmpty(appConfig, "App config must be provided");

        GenParserConfig parserConfig = new GenParserConfig();

        parserConfigLoader = new PropertiesParserConfigLoader();
        parserConfigLoader.load(parserConfig, parserConfigProperties);

        Dictionary dictionary = new Dictionary();

        if (ObjectHelper.isEmpty(getDictionaryElements())) {
            if (getFormat() == ParserFormat.FIXEDLENGTH || getFormat() == ParserFormat.CSV) {
                dictionaryProperties.addDummyElement();
            } else {
                throw new IllegalArgumentException("Dictionary must have at least one element");
            }
        }

        dictionaryLoader = new PropertiesDictionaryLoader();
        dictionaryLoader.load(dictionary, dictionaryProperties);
        parserConfig.setDictionary(dictionary);

        //Setup AppConfig
        appConfigLoader = new XmlAppConfigLoader();
        appConfigLoader.load(parserConfig, getAppConfig());

        //Parse Message
        try {
            EntityFactory entityFactory = SimpleEntityFactory.getInstance();
            ParserFactory parserFactory = ParserFactory.getInstance();
            Parser parser = parserFactory.getParser(null, parserConfig, entityFactory);
            Entity entity = parser.parse(streamReader);
            return entity;
        } finally {
            IOHelper.close(streamReader);
        }

    }

    @Override
    public String getDataFormatName() {
        return "GwhParser";
    }

    @Override
    public CamelContext getCamelContext() {
        return camelContext;
    }

    @Override
    public void setCamelContext(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    //----- Methods for Parser Config -----//
    public ParserFormat getFormat() {
        return parserConfigProperties.getFormat();
    }

    public void setFormat(ParserFormat format) {
        parserConfigProperties.setFormat(format);
    }

    public String getCharacterSet() {
        return parserConfigProperties.getCharacterSet();
    }

    public void setCharacterSet(String characterSet) {
        parserConfigProperties.setCharacterSet(characterSet);
    }

    public boolean isCoalescing() {
        return parserConfigProperties.isCoalescing();
    }

    public void setCoalescing(boolean coalescing) {
        parserConfigProperties.setCoalescing(coalescing);
    }

    public List<DateTimeFormatter> getDateFormatters() {
        return parserConfigProperties.getDateFormatters();
    }

    public void setDateFormatters(List<DateTimeFormatter> dateFormatters) {
        parserConfigProperties.setDateFormatters(dateFormatters);
    }

    public ValueType getDefaultValueType() {
        return parserConfigProperties.getDefaultValueType();
    }

    public void setDefaultValueType(ValueType defaultValueType) {
        parserConfigProperties.setDefaultValueType(defaultValueType);
    }

    public boolean isPreserveCDATA() {
        return parserConfigProperties.isPreserveCDATA();
    }

    public void setPreserveCDATA(boolean preserveCDATA) {
        parserConfigProperties.setPreserveCDATA(preserveCDATA);
    }

    public String getRootEntityName() {
        return parserConfigProperties.getRootEntityName();
    }

    public void setRootEntityName(String rootEntityName) {
        parserConfigProperties.setRootEntityName(rootEntityName);
    }

    public String getStringConcatDelimiter() {
        return parserConfigProperties.getStringConcatDelimiter();
    }

    public void setStringConcatDelimiter(String stringConcatDelimiter) {
        parserConfigProperties.setStringConcatDelimiter(stringConcatDelimiter);
    }

    public List<DateTimeFormatter> getTimestampFormatters() {
        return parserConfigProperties.getTimestampFormatters();
    }

    public void setTimestampFormatters(List<DateTimeFormatter> timestampFormatters) {
        parserConfigProperties.setTimestampFormatters(timestampFormatters);
    }

    public void setCsvDelimiter(char delimiter) {
        parserConfigProperties.setCsvDelimiter(delimiter);
    }

    public void setSkipHeader(boolean skipHeader) {
        parserConfigProperties.setSkipHeader(skipHeader);
    }

    public void setUseMap(boolean useMap) {
        parserConfigProperties.setUseMap(useMap);
    }

    //----- Methods for Datadictionary -----//
    public String getDictionaryName() {
        return dictionaryProperties.getDictionaryName();
    }

    public void setDictionaryName(String dictionaryName) {
        dictionaryProperties.setDictionaryName(dictionaryName);
    }

    public List<Element> getDictionaryElements() {
        return dictionaryProperties.getElements();
    }

    public void setDictionaryElements(List<Element> elements) {
        dictionaryProperties.setElements(elements);
    }

    public void addDictionaryElement(Element element) {
        dictionaryProperties.addElement(element);
    }

    public String getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(String appConfig) {
        this.appConfig = appConfig;
    }

}
