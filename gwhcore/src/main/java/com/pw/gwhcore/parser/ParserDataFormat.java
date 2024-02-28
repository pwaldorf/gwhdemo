package com.pw.gwhcore.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spi.DataFormatName;
import org.apache.camel.support.service.ServiceSupport;
import org.apache.camel.util.IOHelper;

import com.parser.gwhparser.parser.Parser;
import com.parser.gwhparser.parser.ParserFactory;
import com.parser.gwhparser.parser.SampleReader;
import com.parser.gwhparser.parser.SampleReaderFactory;
import com.parser.gwhparser.parser.config.GenParserConfig;
//import com.parser.gwhparser.parser.config.ParserConfig;
import com.parser.gwhparser.parser.config.ParserFormat;
import com.parser.gwhparser.parser.config.PathMapping;
import com.parser.gwhparser.parser.config.ValueType;
import com.parser.gwhparser.parser.model.ConfigDictionaryLoader;
import com.parser.gwhparser.parser.model.Dictionary;
import com.parser.gwhparser.parser.model.DictionaryLoader;
import com.parser.gwhparser.parser.model.ElementConfig;
import com.parser.gwhparser.parser.model.ElementConfigs;
import com.parser.gwhparser.parser.model.Entity;
import com.parser.gwhparser.parser.model.EntityFactory;
import com.parser.gwhparser.parser.model.SimpleEntityFactory;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
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

    public static final String DEFAULT_DICTIONARY = "<Dictionary>"
            + "<DictionaryName>" + "DEFAULT" + "</DictionaryName>"
            + "<Element id=\"1\" name=\"FIRST_NAME\" />"
            + "<Element id=\"2\" name=\"LAST_NAME\" />"
            + "<Element id=\"3\" name=\"AGE\" type=\"INTEGER\" />"
            + "</Dictionary>";

    public static final String ELEMENT_CONFIGS = "<ElementConfigs>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<length>10</length>"
            + "<name>firstname</name>"
            + "<start>0</start>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<length>10</length>"
            + "<name>lastname</name>"
            + "<start>10</start>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<length>2</length>"
            + "<name>age</name>"
            + "<start>20</start>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "</ElementConfigs>";

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

        DictionaryLoader<String> dictionaryLoader = new ConfigDictionaryLoader();
        dictionaryLoader.load(DEFAULT_DICTIONARY);
        log.info("Dictionary: " + Dictionary.getInstance().toString());

        EntityFactory entityFactory = SimpleEntityFactory.getInstance();        
        GenParserConfig parserConfig = createParserConfig(ELEMENT_CONFIGS);
        ParserFactory parserFactory = ParserFactory.getInstance();
        Parser parser = parserFactory.getParser(null, parserConfig, entityFactory);
        Entity entity = parser.parse(data);
        return entity;

    }

    private GenParserConfig createParserConfig(String elementConfigs) throws JAXBException {
                
        GenParserConfig parserConfig = new GenParserConfig();
        parserConfig.setFormat(getFormat());
        parserConfig.setCharacterSet(getCharacterSet());
        parserConfig.setCoalescing(isCoalescing());
        parserConfig.setDateFormatters(getDateFormatters());
        parserConfig.setDefaultValueType(getDefaultValueType());
        parserConfig.setPreserveCDATA(isPreserveCDATA());
        parserConfig.setRootEntityName(getRootEntityName());
        parserConfig.setStringConcatDelimiter(getStringConcatDelimiter());
        parserConfig.setTimestampFormatters(getTimestampFormatters());

        if (parserConfig.getFormat() == ParserFormat.FIXEDLENGTH ||
            parserConfig.getFormat() == ParserFormat.QREP) {        
            parserConfig.setEleConfigs(genEleConfig(elementConfigs));            
        } else {
            parserConfig.setMappings(setElementMappings(elementConfigs));
        }
        

        // Parser Config Name
        parserConfig.setSampleName("MYAPP");
        // Message Format
        parserConfig.setFormat(getFormat());
        return parserConfig;
                            
    }

    private List<ElementConfig> genEleConfig(String context) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(ElementConfigs.class);
        Unmarshaller Unmarshaller = jaxbContext.createUnmarshaller();
        ElementConfigs eleConfigs = (ElementConfigs) Unmarshaller.unmarshal(new StringReader(context));
        return eleConfigs.getElements();

    }

    @SuppressWarnings("unchecked")    
    private Iterator<Entry<String, List<PathMapping>>> setElementMappings(String context) {
        
        SampleReaderFactory readerFactory = SampleReaderFactory.getInstance();
        SampleReader reader = readerFactory.getReader(ParserFormat.XML);
        return (Iterator<Entry<String, List<PathMapping>>>) reader.readSample(context);

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
    
    
}
