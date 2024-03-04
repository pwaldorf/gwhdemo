package com.pw.gwhcore.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spi.DataFormatName;
import org.apache.camel.support.service.ServiceSupport;
import org.apache.camel.util.IOHelper;

import com.parser.gwhparser.loader.ConfigLoaderStrategy;
import com.parser.gwhparser.loader.appconfig.XmlAppConfigLoader;
import com.parser.gwhparser.loader.dictionaryconfig.ConfigDictionaryLoader;
import com.parser.gwhparser.loader.parserconfig.XmlParserConfigLoader;
import com.parser.gwhparser.parser.Parser;
import com.parser.gwhparser.parser.ParserFactory;
import com.parser.gwhparser.parser.config.GenParserConfig;
import com.parser.gwhparser.parser.enums.ParserFormat;
import com.parser.gwhparser.parser.enums.ValueType;
import com.parser.gwhparser.parser.model.Entity;
import com.parser.gwhparser.parser.model.EntityFactory;
import com.parser.gwhparser.parser.model.SimpleEntityFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParserDataFormat extends ServiceSupport implements DataFormat, DataFormatName, CamelContextAware {

    private transient CamelContext camelContext;

    public static final ParserFormat DEFAULT_PARSER_FORMAT = ParserFormat.XML;
    public static final String DEFAULT_CHARACTER_SET = "UTF-8";
    public static final ValueType DEFAULT_VALUE_TYPE = ValueType.STRING;
    public static final String DEFAULT_ROOT_ENTITY_NAME = "ROOT";
    public static final String DEFAULT_CONCAT_DELIMITER = " ";

    private ParserFormat format = DEFAULT_PARSER_FORMAT;
    private String characterSet = DEFAULT_CHARACTER_SET;
    private boolean coalescing = false;
    private List<DateTimeFormatter> dateFormatters = Collections.emptyList();
    private ValueType defaultValueType = DEFAULT_VALUE_TYPE;
    private boolean preserveCDATA = false;
    private String rootEntityName = DEFAULT_ROOT_ENTITY_NAME;
    private String stringConcatDelimiter = DEFAULT_CONCAT_DELIMITER;
    private List<DateTimeFormatter> timestampFormatters = Collections.emptyList();

    private ConfigLoaderStrategy<String, GenParserConfig> dictionaryLoader = null;
    private String dictionaryConfig = null;
    private ConfigLoaderStrategy<String, GenParserConfig> parserConfigLoader = null;
    private String parserConfig = null;
    private String appConfig = null;

    public static final String DEFAULT_DICTIONARY = "<Dictionary>"
            + "<DictionaryName>" + "DEFAULT" + "</DictionaryName>"
            + "<Element id=\"1\" name=\"DEFAULT_ELEMENT\" description=\"Default Element\" />"
            + "</Dictionary>";

    public static final String DEFAULT_PARSER_PROPERTIES = "<ParserConfig>"
            + "<Property name=\"Format\" value=\"XML\" />"
            + "<Property name=\"CharacterSet\" value=\"UTF-8\" />"
            + "<Property name=\"DateFormat\" value=\"yyyy-MM-dd HH:mm:ss\" />"
            + "<Property name=\"TimestampFormat\" value=\"ISO_OFFSET_DATE_TIME\" />"
            + "</ParserConfig>";

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

    @Override
    public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'marshal'");
    }

    @Override
    public Object unmarshal(Exchange exchange, InputStream stream) throws Exception {
        
        BufferedReader streamReader = IOHelper.buffered(new InputStreamReader(stream, getCharacterSet()));
        String data = exchange.getContext().getTypeConverter().mandatoryConvertTo(String.class, exchange, streamReader);

        //Setup DictionaryConfig                
        ConfigLoaderStrategy<String, GenParserConfig> dictionaryLoader = new ConfigDictionaryLoader(getDictionaryConfig(), new GenParserConfig());
        
        //Setup ParserConfig
        GenParserConfig parserConfig = new GenParserConfig();
        ConfigLoaderStrategy<String, GenParserConfig> xmlParserConfigLoader = new XmlParserConfigLoader(getParserConfig(), parserConfig);        

        //Setup AppConfig
        ConfigLoaderStrategy<String, GenParserConfig> appConfigLoader = new XmlAppConfigLoader(getAppConfig(), parserConfig);

        //Parse Message
        EntityFactory entityFactory = SimpleEntityFactory.getInstance();
        ParserFactory parserFactory = ParserFactory.getInstance();        
        Parser parser = parserFactory.getParser(null, parserConfig, entityFactory);
        Entity entity = parser.parse(data);
        return entity;

    }

    public ParserFormat getFormat() {
        return format;
    }

    public void setFormat(ParserFormat format) {
        this.format = format;
    }

    public String getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }

    public boolean isCoalescing() {
        return coalescing;
    }

    public void setCoalescing(boolean coalescing) {
        this.coalescing = coalescing;
    }

    public List<DateTimeFormatter> getDateFormatters() {
        return (dateFormatters != null ? dateFormatters : Collections.emptyList());
    }

    public void setDateFormatters(List<DateTimeFormatter> dateFormatters) {
        this.dateFormatters = dateFormatters;
    }

    public ValueType getDefaultValueType() {
        return defaultValueType;
    }

    public void setDefaultValueType(ValueType defaultValueType) {
        this.defaultValueType = defaultValueType;
    }

    public boolean isPreserveCDATA() {
        return preserveCDATA;
    }

    public void setPreserveCDATA(boolean preserveCDATA) {
        this.preserveCDATA = preserveCDATA;
    }

    public String getRootEntityName() {
        return rootEntityName;
    }

    public void setRootEntityName(String rootEntityName) {
        this.rootEntityName = rootEntityName;
    }

    public String getStringConcatDelimiter() {
        return stringConcatDelimiter;
    }

    public void setStringConcatDelimiter(String stringConcatDelimiter) {
        this.stringConcatDelimiter = stringConcatDelimiter;
    }

    public List<DateTimeFormatter> getTimestampFormatters() {
        return timestampFormatters;
    }

    public void setTimestampFormatters(List<DateTimeFormatter> timestampFormatters) {
        this.timestampFormatters = timestampFormatters;
    }

    public ConfigLoaderStrategy<String, GenParserConfig> getDictionaryLoader() {
        return (dictionaryLoader != null ? dictionaryLoader : new ConfigDictionaryLoader());
    }

    public void setDictionaryLoader(ConfigLoaderStrategy<String, GenParserConfig> dictionaryLoader) {
        this.dictionaryLoader = dictionaryLoader;
    }

    public String getDictionaryConfig() {
        return (dictionaryConfig != null ? dictionaryConfig : DEFAULT_DICTIONARY);
    }

    public void setDictionaryConfig(String dictionaryConfig) {
        this.dictionaryConfig = dictionaryConfig;
    }

    public ConfigLoaderStrategy<String, GenParserConfig> getParserConfigLoader() {
        return (parserConfigLoader != null ? parserConfigLoader : new XmlParserConfigLoader());
    }

    public void setParserConfigLoader(ConfigLoaderStrategy<String, GenParserConfig> parserConfigLoader) {
        this.parserConfigLoader = parserConfigLoader;
    }

    public String getParserConfig() {
        return (parserConfig != null ? parserConfig : DEFAULT_PARSER_PROPERTIES);
    }

    public void setParserConfig(String parserConfig) {
        this.parserConfig = parserConfig;
    }

    public String getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(String appConfig) {
        this.appConfig = appConfig;
    }
    
}
