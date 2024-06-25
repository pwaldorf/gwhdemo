package com.pw.dataformat1.flatpackdataformat.loader;

public class GwhFlatpackDataResource {

    private String dataformatDefinition;

    public String getDataformatDefinition() {
        return dataformatDefinition;
    }

    public void setDataformatDefinition(String dataformatDefinition) {
        this.dataformatDefinition = dataformatDefinition;
    }

    // public String getDataformatDefinition() {
    //     return new StringBuilder("<?xml version='1.0'?>")
    //             .append("<!DOCTYPE PZMAP SYSTEM \"flatpack.dtd\" >")
    //             .append("<PZMAP>")
    //             .append("<RECORD id=\"header\" indicator=\"Index\" elementNumber=\"1\">")
    //             .append("<COLUMN name=\"Header1\" />")
    //             .append("<COLUMN name=\"Header2\" />")
    //             .append("<COLUMN name=\"Header3\" />")
    //             .append("<COLUMN name=\"Header4\" />")
    //             .append("<COLUMN name=\"Header5\" />")
    //             .append("<COLUMN name=\"Header6\" />")
    //             .append("<COLUMN name=\"Header7\" />")
    //             .append("<COLUMN name=\"Header8\" />")
    //             .append("<COLUMN name=\"Header9\" />")
    //             .append("<COLUMN name=\"Header10\" />")
    //             .append("<COLUMN name=\"Header11\" />")
    //             .append("<COLUMN name=\"Header12\" />")
    //             .append("</RECORD>")
    //             .append("<COLUMN name=\"Index\" />")
    //             .append("<COLUMN name=\"Customer Id\" />")
    //             .append("<COLUMN name=\"First Name\" />")
    //             .append("<COLUMN name=\"Last Name\" />")
    //             .append("<COLUMN name=\"Company\" />")
    //             .append("<COLUMN name=\"City\" />")
    //             .append("<COLUMN name=\"Country\" />")
    //             .append("<COLUMN name=\"Phone 1\" />")
    //             .append("<COLUMN name=\"Phone 2\" />")
    //             .append("<COLUMN name=\"Email\" />")
    //             .append("<COLUMN name=\"Subscription Data\" />")
    //             .append("<COLUMN name=\"Website\" />")
    //             .append("</PZMAP>")
    //             .toString();
    // }
}
