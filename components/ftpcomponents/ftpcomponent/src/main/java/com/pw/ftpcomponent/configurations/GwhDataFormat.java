package com.pw.ftpcomponent.configurations;


import org.apache.camel.spi.DataFormat;


public interface GwhDataFormat {

    DataFormat geDataFormat(String formatName);

}
