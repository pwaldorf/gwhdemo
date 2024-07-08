-- ### Dispatcher Example
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (1, 'dispatcher', 'local', 'v1', 'dispatch_reader_1a', 'templateId', 'activemqdefault_reader_v1');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (2, 'dispatcher', 'local', 'v1', 'dispatch_reader_1a', 'queue', 'test.queue2');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (3, 'dispatcher', 'local', 'v1', 'dispatch_reader_1a', 'directName', 'writeeventstore');

INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (4, 'dispatcher', 'local', 'v1', 'dispatch_reader_1b', 'templateId', 'kafka_writer_v1');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (5, 'dispatcher', 'local', 'v1', 'dispatch_reader_1b', 'directName', 'writeeventstore');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (6, 'dispatcher', 'local', 'v1', 'dispatch_reader_1b', 'topic', 'test_topic');

-- ### ftp Example
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (20, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1a', 'templateId', 'ftp_reader_v1');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (21, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1a', 'directName', 'fileparserandgroup');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (22, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1a', 'ftpusername', 'anonymous');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (23, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1a', 'ftppassword', '1234');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (24, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1a', 'ftpserver', 'localhost');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (25, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1a', 'ftpport', '2021');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (26, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1a', 'directory', 'pub');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (27, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1a', 'fileName', 'customers-100.csv');

INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (28, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1b', 'templateId', 'fileparserandgroup');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (29, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1b', 'directNameOut', 'writejmsqueue');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (30, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1b', 'groupCount', '5');
       INSERT INTO route_template_params (id, profile, version, region, route_id, template_param_name, template_param_value)
       VALUES (31, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1b', 'formatName', 'customers-100.csv');

INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (32, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1c', 'templateId', 'activemqdefault_writer_v1');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (33, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1c', 'directName', 'writejmsqueue');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (34, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1c', 'queue', 'test.queue1');

INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (35, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1d', 'templateId', 'activemqdefault_reader_v1');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (36, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1d', 'queue', 'test.queue1');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (37, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1d', 'directName', 'logger');

-- ### ftp2 Test Example
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (60, 'ftptest2', 'local', 'v1', 'ftp2_reader_1a', 'templateId', 'ftp_reader_v1');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (61, 'ftptest2', 'local', 'v1', 'ftp2_reader_1a', 'directName', 'fileparserandgroup2');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (62, 'ftptest2', 'local', 'v1', 'ftp2_reader_1a', 'ftpusername', 'anonymous');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (63, 'ftptest2', 'local', 'v1', 'ftp2_reader_1a', 'ftppassword', '1234');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (64, 'ftptest2', 'local', 'v1', 'ftp2_reader_1a', 'ftpserver', 'localhost');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (65, 'ftptest2', 'local', 'v1', 'ftp2_reader_1a', 'ftpport', '2021');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (66, 'ftptest2', 'local', 'v1', 'ftp2_reader_1a', 'directory', 'pub');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (67, 'ftptest2', 'local', 'v1', 'ftp2_reader_1a', 'fileName', 'test-ftp2.txt');

INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (68, 'ftptest2', 'local', 'v1', 'ftp2_reader_1b', 'templateId', 'fileparserandgroup2');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (69, 'ftptest2', 'local', 'v1', 'ftp2_reader_1b', 'directNameOut', 'logger');
INSERT INTO route_template_params (id, profile, region, version, route_id, template_param_name, template_param_value)
       VALUES (70, 'ftptest2', 'local', 'v1', 'ftp2_reader_1b', 'groupCount', '5');


-- ### Example Routes
-- normalize to make routes reusable
INSERT INTO routes (id, profile, region, version, route_id, route)
       VALUES (1, 'dispatcher', 'local', 'v1', 'directlogger', '<route id="directlogger"><from uri="direct:logger"/><log message="Direct Logger Table: ${body}"/></route>');
INSERT INTO routes (id, profile, region, version, route_id, route)
       VALUES (2, 'ftptestprofile', 'local', 'v1', 'directlogger', '<route id="directlogger"><from uri="direct:logger"/><log message="Direct Logger Table: ${body}"/></route>');
INSERT INTO routes (id, profile, region, version, route_id, route)
       VALUES (3, 'ftptest2', 'local', 'v1', 'directlogger', '<route id="directlogger"><from uri="direct:logger"/><log message="Direct Logger Table: ${body}"/></route>');

-- ### Example Cache Configuration
INSERT INTO cache_configuration (id, profile, region, version, cache_name, cache_initial_capacity, cache_maximum_size, cache_eviction_type, cache_expire_after_access_time, cache_expire_after_write_time, cache_stats_enabled, cache_stats_name, cache_loader_name)
       VALUES (1, 'dispatcher', 'local', 'v1', 'testcache', 1000, 10000, 'size_based', 0, 0, 0, '', '');

-- ### Example Route Properties for UI Selection
INSERT INTO route_properties (id, profile, region, version, route_id, property_key, property_value)
       VALUES (1, 'dispatcher', 'local', 'v1', 'dispatch_reader_1a', 'routeType', 'Consumer');
INSERT INTO route_properties (id, profile, region, version, route_id, property_key, property_value)
       VALUES (20, 'ftptestprofile', 'local', 'v1', 'ftp_reader_1a', 'routeType', 'Consumer');
INSERT INTO route_properties (id, profile, region, version, route_id, property_key, property_value)
       VALUES (30, 'ftptest2', 'local', 'v1', 'ftp_reader_1a', 'routeType', 'Consumer');

-- ### FTP Example Properties
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (1, 'ftptestprofile', 'local', 'v1', 'ftp.consumer');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (2, 'ftptestprofile', 'local', 'v1', 'routes');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (3, 'ftptestprofile', 'local', 'v1', 'routetemplates');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (4, 'ftptestprofile', 'local', 'v1', 'routemanagement');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (5, 'ftptestprofile', 'local', 'v1', 'routemanagement.rest');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (6, 'ftptestprofile', 'local', 'v1', 'route.properties');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (7, 'ftptestprofile', 'local', 'v1', 'datasource');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (8, 'ftptestprofile', 'local', 'v1', 'splitter');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (9, 'ftptestprofile', 'local', 'v1', 'dataformat');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (10, 'ftptestprofile', 'local', 'v1', 'activemq');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (11, 'ftptestprofile', 'local', 'v1', 'activemq.producer');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (12, 'ftptestprofile', 'local', 'v1', 'activemq.consumer');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (13, 'ftptestprofile', 'local', 'v1', 'local.ftp');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (14, 'ftptestprofile', 'local', 'v1', 'local.jms');

-- ### Dispatcher Example Properties
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (20, 'dispatcher', 'local', 'v1', 'routes');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (21, 'dispatcher', 'local', 'v1', 'routetemplates');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (22, 'dispatcher', 'local', 'v1', 'routemanagement');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (23, 'dispatcher', 'local', 'v1', 'routemanagement.rest');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (24, 'dispatcher', 'local', 'v1', 'activemq');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (25, 'dispatcher', 'local', 'v1', 'activemq.consumer');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (26, 'dispatcher', 'local', 'v1', 'kafka');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (27, 'dispatcher', 'local', 'v1', 'kafka.producer');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (28, 'dispatcher', 'local', 'v1', 'datasource');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (29, 'dispatcher', 'local', 'v1', 'route.properties');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (30, 'dispatcher', 'local', 'v1', 'local.jms');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (31, 'dispatcher', 'local', 'v1', 'local.kafka');

-- ### FTP 2 Example Properties
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (51, 'ftptest2', 'local', 'v1', 'ftp.consumer');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (52, 'ftptest2', 'local', 'v1', 'routes');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (53, 'ftptest2', 'local', 'v1', 'routetemplates');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (54, 'ftptest2', 'local', 'v1', 'routemanagement');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (55, 'ftptest2', 'local', 'v1', 'routemanagement.rest');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (56, 'ftptest2', 'local', 'v1', 'route.properties');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (57, 'ftptest2', 'local', 'v1', 'datasource');
-- INSERT INTO profile_properties (id, profile, region, version, property) VALUES (58, 'ftptest2', 'local', 'v1', 'splitter');
-- INSERT INTO profile_properties (id, profile, region, version, property) VALUES (59, 'ftptest2', 'local', 'v1', 'dataformat');
-- INSERT INTO profile_properties (id, profile, region, version, property) VALUES (60, 'ftptest2', 'local', 'v1', 'activemq');
-- INSERT INTO profile_properties (id, profile, region, version, property) VALUES (61, 'ftptest2', 'local', 'v1', 'activemq.producer');
-- INSERT INTO profile_properties (id, profile, region, version, property) VALUES (62, 'ftptest2', 'local', 'v1', 'activemq.consumer');
INSERT INTO profile_properties (id, profile, region, version, property) VALUES (63, 'ftptest2', 'local', 'v1', 'local.ftp');
-- INSERT INTO profile_properties (id, profile, region, version, property) VALUES (64, 'ftptest2', 'local', 'v1', 'local.jms');


-- ### Properties
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (1, 'local', 'v1', 'ftp.consumer', 'gwh.component.ftp.default1.consumer.protocol', 'ftp');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (2, 'local', 'v1', 'ftp.consumer', 'gwh.framework.component.ftp.default1.consumer.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (3, 'local', 'v1', 'routes', 'gwh.framework.load.routes.core1.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (4, 'local', 'v1', 'routes', 'gwh.framework.load.routes.jpa1.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (5, 'local', 'v1', 'route.properties', 'gwh.framework.load.routes.core1.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (6, 'local', 'v1', 'route.properties', 'gwh.framework.load.route.properties.jpa1.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (7, 'local', 'v1', 'routetemplates', 'gwh.framework.load.routetemplates.core1.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (8, 'local', 'v1', 'routetemplates', 'gwh.framework.load.routetemplates.jpa1.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (9, 'local', 'v1', 'routemanagement', 'gwh.framework.core.routemanagement.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (10, 'local', 'v1', 'routemanagement.rest', 'gwh.framework.component.routemanagement.rest1.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (11, 'local', 'v1', 'activemq', 'gwh.component.activemq.default1.broker-url', 'tcp://localhost:61617');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (12, 'local', 'v1', 'activemq', 'gwh.component.activemq.default1.username', 'artemis');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (13, 'local', 'v1', 'activemq', 'gwh.component.activemq.default1.password', 'artemis');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (14, 'local', 'v1', 'activemq', 'gwh.component.activemq.default1.session-cache-size', '10');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (15, 'local', 'v1', 'activemq', 'gwh.framework.component.activemq.default1.default.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (16, 'local', 'v1', 'activemq.consumer', 'gwh.framework.component.activemq.default1.consumer.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (17, 'local', 'v1', 'activemq.producer', 'gwh.framework.component.activemq.default1.producer.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (18, 'local', 'v1', 'kafka', 'gwh.component.kafka.default1.consumer.brokers', 'tcp://localhost:9093');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (19, 'local', 'v1', 'kafka', 'gwh.component.kafka.default1.producer.brokers', 'tcp://localhost:9093');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (20, 'local', 'v1', 'kafka', 'gwh.framework.component.kafka.default1.default.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (21, 'local', 'v1', 'kafka.consumer', 'gwh.framework.component.kafka.default1.consumer.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (22, 'local', 'v1', 'kafka.producer', 'gwh.framework.component.kafka.default1.producer.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (23, 'local', 'v1', 'pgp', 'gwh.framework.component.pgpencryption.default1.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (24, 'local', 'v1', 'pgp', 'gwh.component.pgp.default1.public-key-user-id', 'pw company <philip.waldorf@gmail.com>');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (25, 'local', 'v1', 'pgp', 'gwh.component.pgp.default1.public-key-password', 'XXXXXXXX');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (26, 'local', 'v1', 'pgp', 'gwh.component.pgp.default1.public-key-file-name', 'file:/home/pwaldorf/.gnupg/pubring.gpg');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (27, 'local', 'v1', 'pgp', 'gwh.component.pgp.default1.private-key-user-id', 'pw company <philip.waldorf@gmail.com>');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (28, 'local', 'v1', 'pgp', 'gwh.component.pgp.default1.private-key-password', 'XXXXXXXX');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (29, 'local', 'v1', 'pgp', 'gwh.component.pgp.default1.private-key-file-name', 'file:/home/pwaldorf/.gnupg/secring.gpg');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (30, 'local', 'v1', 'datasource', 'gwh.db.datasource.className', 'org.h2.Driver');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (31, 'local', 'v1', 'datasource', 'gwh.db.datasource.url', 'jdbc:h2:mem:gwh');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (32, 'local', 'v1', 'datasource', 'gwh.db.datasource.userName', 'pwaldorf');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (33, 'local', 'v1', 'datasource', 'gwh.db.datasource.password', 'pwaldorf');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (34, 'local', 'v1', 'datasource', 'gwh.db.datasource.minpoolsize', '0');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (35, 'local', 'v1', 'datasource', 'gwh.db.datasource.maxpoolsize', '50');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (36, 'local', 'v1', 'datasource', 'gwh.db.datasource.increment', '2');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (37, 'local', 'v1', 'datasource', 'gwh.db.datasource.maxidleTime', '300');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (38, 'local', 'v1', 'datasource', 'gwh.db.datasource.retryDelay', '600000');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (39, 'local', 'v1', 'datasource', 'gwh.db.datasource.databaseType', 'H2');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (40, 'local', 'v1', 'datasource', 'gwh.db.datasource.jpa-dialect', 'org.hibernate.dialect.H2Dialect');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (41, 'local', 'v1', 'datasource', 'gwh.db.datasource.jpa-ddl-auto', 'validate');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (42, 'local', 'v1', 'datasource', 'gwh.db.datasource.jpa-physical-naming-strategy', 'org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (43, 'local', 'v1', 'datasource', 'gwh.db.datasource.jpa-properties.hibernate.hbm2ddl.auto', 'validate');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (44, 'local', 'v1', 'datasource', 'gwh.db.datasource.jpa-properties.hibernate.physical.naming.strategy', 'org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy');

INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (45, 'local', 'v1', 'splitter', 'gwh.framework.component.dataformat.flatpack.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (46, 'local', 'v1', 'dataformat', 'gwh.dataformat.format.name', 'customers-100.csv');

INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (1000, 'local', 'v1', 'local.ftp', 'gwh.framework.devtool.ftp.server.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (1001, 'local', 'v1', 'local.activemq', 'gwh.framework.devtool.activemq.server.enabled', 'true');
INSERT INTO properties (id, region, version, property, property_key, property_value) VALUES (1002, 'local', 'v1', 'local.kafka', 'gwh.framework.devtool.kafka.broker.enabled', 'true');

-- ### FTP Example Data Format
INSERT INTO dataformat_configuration (id, profile, region, version, dataformat_name, dataformat_config, dataformat_definition)
       VALUES (1, 'ftptestprofile', 'local', 'v1', 'customers-100.csv',
              '{
       "delimiter": ",",
       "textQualifier": "\"",
       "fixedFile": false,
       "formatDefinition": "bean:gwhFlatpackDataResource?method=getDataformatDefinition",
       "ignoreFirstRecord": false,
       "allowShortLines": true,
       "ignoreExtraColumns": false
       }',
              '<?xml version="1.0"?>
                <!DOCTYPE PZMAP SYSTEM "flatpack.dtd" >
                     <PZMAP>
                            <RECORD id="header" indicator="Index" elementNumber="1">
                                   <COLUMN name="Header1" />
                                   <COLUMN name="Header2" />
                                   <COLUMN name="Header3" />
                                   <COLUMN name="Header4" />
                                   <COLUMN name="Header5" />
                                   <COLUMN name="Header6" />
                                   <COLUMN name="Header7" />
                                   <COLUMN name="Header8" />
                                   <COLUMN name="Header9" />
                                   <COLUMN name="Header10" />
                                   <COLUMN name="Header11" />
                                   <COLUMN name="Header12" />
                            </RECORD>
                            <COLUMN name="Index" />
                            <COLUMN name="Customer Id" />
                            <COLUMN name="First Name" />
                            <COLUMN name="Last Name" />
                            <COLUMN name="Company" />
                            <COLUMN name="City" />")
                            <COLUMN name="Country" />
                            <COLUMN name="Phone 1" />
                            <COLUMN name="Phone 2" />
                            <COLUMN name="Email" />
                            <COLUMN name="Subscription Data" />
                            <COLUMN name="Website" />
                     </PZMAP>');