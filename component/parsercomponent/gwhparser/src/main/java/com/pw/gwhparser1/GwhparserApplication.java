package com.pw.gwhparser1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pw.gwhparser1.loader.ConfigLoaderStrategy;
import com.pw.gwhparser1.loader.appconfig.XmlAppConfigLoader;
import com.pw.gwhparser1.loader.dictionaryconfig.DictionaryProperties;
import com.pw.gwhparser1.loader.dictionaryconfig.PropertiesDictionaryLoader;
import com.pw.gwhparser1.loader.dictionaryconfig.XmlDictionaryLoader;
import com.pw.gwhparser1.loader.parserconfig.ParserConfigProperties;
import com.pw.gwhparser1.loader.parserconfig.PropertiesParserConfigLoader;
import com.pw.gwhparser1.loader.parserconfig.XmlParserConfigLoader;
import com.pw.gwhparser1.parser.Parser;
import com.pw.gwhparser1.parser.ParserFactory;
import com.pw.gwhparser1.parser.config.GenParserConfig;
import com.pw.gwhparser1.parser.enums.CsvDelimiter;
import com.pw.gwhparser1.parser.enums.ParserFormat;
import com.pw.gwhparser1.parser.model.Dictionary;
import com.pw.gwhparser1.parser.model.Entity;
import com.pw.gwhparser1.parser.model.EntityFactory;
import com.pw.gwhparser1.parser.model.SimpleEntityFactory;

import javax.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class GwhparserApplication {

    private static final String DEFAULT_PARSER_PROPERTIES = "<ParserConfig>"
            + "<Property name=\"Format\" value=\"FIXEDLENGTH\" />"
            + "<Property name=\"CharacterSet\" value=\"UTF-8\" />"
            + "<Property name=\"DateFormat\" value=\"yyyy-MM-dd HH:mm:ss\" />"
            + "<Property name=\"TimestampFormat\" value=\"ISO_OFFSET_DATE_TIME\" />"
            + "</ParserConfig>";

    private static final String DEFAULT_DICTIONARY = "<Dictionary>"
            + "<DictionaryName>" + "DEFAULT" + "</DictionaryName>"
            + "<Element id=\"1\" name=\"FIRST_NAME\" description=\"First Name\" />"
            // + "<Element id=\"2\" name=\"LAST_NAME\" />"
            // + "<Element id=\"3\" name=\"AGE\" type=\"INTEGER\" />"
            + "</Dictionary>";

	//private static final String MESSAGE = "PHILIP    WALDORF   26";
    private static final String MESSAGE = "PHILIP|WALDORF|26" + "\n" + "AMY|WALDORF|02";

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

    public static final String CSV_ELEMENT_CONFIGS = "<ElementConfigs>"
            + "<ElementConfig>"
            + "<format></format>"
          //  + "<length>10</length>"
            + "<name>firstname</name>"
            + "<index>0</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
          //  + "<length>10</length>"
            + "<name>lastname</name>"
            + "<index>1</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
          //  + "<length>2</length>"
            + "<name>age</name>"
            + "<index>2</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "</ElementConfigs>";


	public static void main(String[] args) throws JAXBException {

		SpringApplication.run(GwhparserApplication.class, args);

        // xmlparsertest();

        propertiesparsertest();

	}

    public static void xmlparsertest() {
        //Setup ParserConfig
        GenParserConfig parserConfig = new GenParserConfig();
        Dictionary dictionary = new Dictionary();

        ConfigLoaderStrategy<Dictionary, String> dictionaryLoader = new XmlDictionaryLoader();
        dictionaryLoader.load(dictionary, DEFAULT_DICTIONARY);
        parserConfig.setDictionary(dictionary);

        ConfigLoaderStrategy<GenParserConfig, String> xmlParserConfigLoader = new XmlParserConfigLoader();
        xmlParserConfigLoader.load(parserConfig, DEFAULT_PARSER_PROPERTIES);

        //Setup AppConfig
        ConfigLoaderStrategy<GenParserConfig, String> appConfigLoader = new XmlAppConfigLoader();
        appConfigLoader.load(parserConfig, ELEMENT_CONFIGS);

        //Parse Message
        EntityFactory entityFactory = SimpleEntityFactory.getInstance();
        ParserFactory parserFactory = ParserFactory.getInstance();
        Parser parser = parserFactory.getParser(null, parserConfig, entityFactory);
        Entity entity = parser.parse(MESSAGE);
		log.info("Entity: " + entity.toString());

    }

    public static void propertiesparsertest() {

        GenParserConfig parserConfig = new GenParserConfig();

        ParserConfigProperties parserConfigProperties = new ParserConfigProperties();
        parserConfigProperties.setFormat(ParserFormat.CSV);
        parserConfigProperties.setCsvDelimiter(CsvDelimiter.PIPE.getDelimiter());
        parserConfigProperties.setUseMap(true);

        ConfigLoaderStrategy<GenParserConfig, ParserConfigProperties> parserConfigLoader = new PropertiesParserConfigLoader();
        parserConfigLoader = new PropertiesParserConfigLoader();
        parserConfigLoader.load(parserConfig, parserConfigProperties);

        Dictionary dictionary = new Dictionary();

        DictionaryProperties dictionaryProperties = new DictionaryProperties();
        dictionaryProperties.addDummyElement();

        ConfigLoaderStrategy<Dictionary, DictionaryProperties> dictionaryLoader = new PropertiesDictionaryLoader();
        dictionaryLoader.load(dictionary, dictionaryProperties);
        parserConfig.setDictionary(dictionary);

        //Setup AppConfig
        ConfigLoaderStrategy<GenParserConfig, String> appConfigLoader = new XmlAppConfigLoader();
        appConfigLoader.load(parserConfig, CSV_ELEMENT_CONFIGS);

        //Parse Message
        EntityFactory entityFactory = SimpleEntityFactory.getInstance();
        ParserFactory parserFactory = ParserFactory.getInstance();
        Parser parser = parserFactory.getParser(null, parserConfig, entityFactory);
        Entity entity = parser.parse(MESSAGE);
		log.info("Entity: " + entity.toString());

    }
}
