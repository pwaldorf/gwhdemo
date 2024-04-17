CREATE TABLE route_template_params (
  id bigint not null AUTO_INCREMENT,
  profile varchar(50) NOT NULL,
  route_id varchar(50) NOT NULL,
  template_param_name varchar(50) NOT NULL,
  template_param_value varchar(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE routes (
  id bigint not null AUTO_INCREMENT,
  profile varchar(50) NOT NULL,
  route_id varchar(50) NOT NULL,
  route varchar(4000) NOT NULL,
  PRIMARY KEY (`id`)
);

create sequence route_template_params_seq start with 1 increment by 50;
create sequence routes_seq start with 1 increment by 50;

CREATE TABLE gwh_configs (
   config_name  VARCHAR(50) NOT NULL,
   config_key   VARCHAR(50) NOT NULL,
   config_value VARCHAR(50),
   PRIMARY KEY (config_name, config_key)
);