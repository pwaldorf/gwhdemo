package com.pw.ftpdefaultcomponent.configurations;


import org.apache.camel.spi.DataFormat;


public interface GwhDataFormat {

    DataFormat getDataFormat(String formatName);

}
