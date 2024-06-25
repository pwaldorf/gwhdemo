package com.pw.jpa.dataformatloader1.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "dataformat_configuration")
@Data
public class GwhDataFormatEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "profile")
    private String profile;

    @Column(name = "region")
    private String region;

    @Column(name = "dataformat_name")
    private String dataFormatName;

    @Column(name = "dataformat_config")
    private String dataFormatConfig;

    @Column(name = "dataformat_definition")
    private String dataFormatDefinition;

}
