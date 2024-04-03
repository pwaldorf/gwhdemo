package com.pw.datafiledataformats.configurations;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GwhFlatpackConfiguration {

    boolean fixedFile;
    char delimiter;
    char textQualifier;
    boolean ignoreFirstRecord;
    String formatDefinition;
}
