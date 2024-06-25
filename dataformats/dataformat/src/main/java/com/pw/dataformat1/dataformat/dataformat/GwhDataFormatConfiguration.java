package com.pw.dataformat1.dataformat.dataformat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GwhDataFormatConfiguration {

    private String dataformatName;
    private String dataformatConfiguration;
    private String dataformatDefinition;
}
