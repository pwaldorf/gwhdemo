package com.pw.dataformat1.dataformat.dataformat;

import org.apache.camel.spi.DataFormat;

public interface GwhDataFormat {

    DataFormat getDataFormat(String formatName);

}
