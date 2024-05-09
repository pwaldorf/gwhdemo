package com.pw.jpapropertiescomponent.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "properties")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class GwhPropertiesEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "profile")
    private String profile;

    @Column(name = "region")
    private String region;

    @Column(name = "property_key")
    private String propertyKey;

    @Column(name = "property_value")
    private String propertyValue;

}
