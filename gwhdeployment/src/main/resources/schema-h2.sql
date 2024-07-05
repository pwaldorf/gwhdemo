CREATE TABLE route_template_params (
  id bigint not null AUTO_INCREMENT,
  profile varchar(50) NOT NULL,
  region varchar(50) NOT NULL,
  version varchar(10) NOT NULL,
  route_id varchar(50) NOT NULL,
  template_param_name varchar(50) NOT NULL,
  template_param_value varchar(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE routes (
  id bigint not null AUTO_INCREMENT,
  profile varchar(50) NOT NULL,
  region varchar(50) NOT NULL,
  version varchar(10) NOT NULL,
  route_id varchar(50) NOT NULL,
  route varchar(4000) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE profile_properties (
   id bigint not null AUTO_INCREMENT,
   profile VARCHAR(50) NOT NULL,
   region  VARCHAR(50) NOT NULL,
   version varchar(10) NOT NULL,
   property  VARCHAR(50) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE properties (
    id bigint not null AUTO_INCREMENT,
    region  VARCHAR(50) NOT NULL,
    version varchar(10) NOT NULL,
    property VARCHAR(50) NOT NULL,
    property_key   VARCHAR(250) NOT NULL,
    property_value VARCHAR(250),
    PRIMARY KEY (id)
);

CREATE TABLE route_properties (
    id bigint not null AUTO_INCREMENT,
    profile VARCHAR(50) NOT NULL,
    region  VARCHAR(50) NOT NULL,
    version varchar(10) NOT NULL,
    route_id varchar(50) NOT NULL,
    property_key   VARCHAR(250) NOT NULL,
    property_value VARCHAR(250),
    PRIMARY KEY (id)
);

CREATE TABLE cache_configuration (
   id bigint not null AUTO_INCREMENT,
   profile VARCHAR(50) NOT NULL,
   region  VARCHAR(50) NOT NULL,
   version varchar(10) NOT NULL,
   cache_name VARCHAR(45) NOT NULL,
   cache_initial_capacity INT NOT NULL,
   cache_maximum_size INT NOT NULL,
   cache_eviction_type VARCHAR(15) NOT NULL,
   cache_expire_after_access_time INT NOT NULL,
   cache_expire_after_write_time INT NOT NULL,
   cache_stats_enabled BOOLEAN NOT NULL,
   cache_stats_name VARCHAR(45) NOT NULL,
   cache_loader_name VARCHAR(45) NOT NULL,
   PRIMARY KEY (id)
);

CREATE TABLE dataformat_configuration (
    id bigint not null AUTO_INCREMENT,
    profile VARCHAR(50) NOT NULL,
    region  VARCHAR(50) NOT NULL,
    version varchar(10) NOT NULL,
    dataformat_name varchar(50) NOT NULL,
    dataformat_config   VARCHAR(1000) NOT NULL,
    dataformat_definition VARCHAR(3000),
    PRIMARY KEY (id)
);

create sequence route_template_params_seq start with 1 increment by 50;
create sequence routes_seq start with 1 increment by 50;
create sequence profile_properties_seq start with 1 increment by 50;
create sequence properties_seq start with 1 increment by 50;
create sequence cache_configuration_seq start with 1 increment by 50;
create sequence hibernate_sequence start with 1 increment by 1;
create sequence dataformat_configuration_seq start with 1 increment by 50;