package com.pw.gwhcore.routes;

import org.apache.camel.LoggingLevel;
//import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.parser.gwhparser.loader.dictionaryconfig.XmlDictionaryLoader;
import com.parser.gwhparser.loader.parserconfig.XmlParserConfigLoader;
import com.parser.gwhparser.parser.enums.ParserFormat;
import com.pw.gwhcore.parser.ParserDataFormat;

@Component
public class LoggingTemplates extends RouteBuilder {

    public static final String MY_PARSER_PROPERTIES = "<ParserConfig>"
            + "<Property name=\"Format\" value=\"FIXEDLENGTH\" />"
            + "<Property name=\"CharacterSet\" value=\"UTF-8\" />"
            + "<Property name=\"DateFormat\" value=\"yyyy-MM-dd HH:mm:ss\" />"
            + "<Property name=\"TimestampFormat\" value=\"ISO_OFFSET_DATE_TIME\" />"
            + "</ParserConfig>";

    public static final String MY_DICTIONARY = "<Dictionary>"
            + "<DictionaryName>" + "DEFAULT" + "</DictionaryName>"
            + "<Element id=\"1\" name=\"FIRST_NAME\" description=\"First Name\" />"
            + "<Element id=\"2\" name=\"LAST_NAME\" />"
            + "<Element id=\"3\" name=\"AGE\" type=\"INTEGER\" />"
            + "</Dictionary>";

    public static final String MY_APP_CONFIG = "<ElementConfigs>"
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
    public void configure() throws Exception {

        ParserDataFormat parser = new ParserDataFormat();
        //parser.setDictionaryLoader(new ConfigDictionaryLoader());
        //parser.setDictionaryConfig(MY_DICTIONARY);
        //parser.setParserConfigLoader(new XmlParserConfigLoader());
        parser.setParserConfig(MY_PARSER_PROPERTIES);
        parser.setAppConfig(MY_APP_CONFIG);


        from("timer:foo?period=10000")
        .setBody(constant("PHILIP    WALDORF   26"))
        .unmarshal(parser)
        .log(LoggingLevel.INFO, org.slf4j.LoggerFactory.getLogger("com.mycompany.mylogger"), "Sending message ${body}");

    }

}
