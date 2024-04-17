package com.pw.dataformats.gatewaydataformat.loader;

import com.pw.dataformats.dataformat.loader.GwhDataLoader;
import com.pw.dataformats.gatewaydataformat.configuration.GwhGatewayConfiguration;
import com.pw.gwhparser.parser.enums.CsvDelimiter;
import com.pw.gwhparser.parser.enums.ParserFormat;


public class GwhJpaGwhDataLoader implements GwhDataLoader<GwhGatewayConfiguration> {

    private String MY_APP_CONFIG_CSV = "<ElementConfigs>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<name>FIRSTNAME</name>"
            + "<index>0</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<name>LASTNAME</name>"
            + "<index>1</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<name>ADDRESS</name>"
            + "<index>2</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<name>CITY</name>"
            + "<index>3</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<name>STATE</name>"
            + "<index>4</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<name>ZIP</name>"
            + "<index>5</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "</ElementConfigs>";

    private String MY_APP_CONFIG_CSV2 = "<ElementConfigs>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<name>FIRSTNAME</name>"
            + "<index>0</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<name>LASTNAME</name>"
            + "<index>1</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            + "<name>AGE</name>"
            + "<index>2</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "</ElementConfigs>";

    private String MY_APP_CONFIG_FIXED = "<ElementConfigs>"
            + "<ElementConfig>"
            + "<format></format>"
           // + "<length>10</length>"
            + "<name>FIRSTNAME</name>"
            + "<index>0</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
           // + "<length>10</length>"
            + "<name>LASTNAME</name>"
            + "<index>1</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            //+ "<length>2</length>"
            + "<name>ADDRESS</name>"
            + "<index>2</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            //+ "<length>2</length>"
            + "<name>CITY</name>"
            + "<index>3</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            //+ "<length>2</length>"
            + "<name>STATE</name>"
            + "<index>4</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "<ElementConfig>"
            + "<format></format>"
            //+ "<length>2</length>"
            + "<name>ZIP</name>"
            + "<index>5</index>"
            + "<type>string</type>"
            + "</ElementConfig>"
            + "</ElementConfigs>";


    @Override
    public GwhGatewayConfiguration load(String formatName) {

        GwhGatewayConfiguration gwhConfiguration = new GwhGatewayConfiguration();
        gwhConfiguration.setFormat(ParserFormat.CSV);
        gwhConfiguration.setCsvDelimiter(CsvDelimiter.PIPE.getDelimiter());
        gwhConfiguration.setUseMap(true);
        gwhConfiguration.setAppConfig(MY_APP_CONFIG_CSV2);
        return gwhConfiguration;
    }





}
