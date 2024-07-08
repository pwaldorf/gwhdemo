package com.pw.support1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormatModel {

    String isHeaderExists;
    String delimiter = ",";
    String content;
    String groupNum;
    Integer headerRowCount;
    Integer footerRowCount;
}
