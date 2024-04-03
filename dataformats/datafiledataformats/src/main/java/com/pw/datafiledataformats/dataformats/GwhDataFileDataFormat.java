package com.pw.datafiledataformats.dataformats;


import org.apache.camel.spi.DataFormat;


public interface GwhDataFileDataFormat {

    DataFormat getDataFormat(String formatName);

}
