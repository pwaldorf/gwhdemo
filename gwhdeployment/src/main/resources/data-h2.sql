INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (1, 'dispatcher', 'dispatch_reader_1a', 'templateId', 'activemqdefault_reader_v1');
INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (2, 'dispatcher', 'dispatch_reader_1a', 'queue', 'test.queue1');
INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (3, 'dispatcher', 'dispatch_reader_1a', 'directname', 'writeeventstore');

INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (4, 'dispatcher', 'dispatch_reader_1b', 'templateId', 'kafka_writer_v1');
INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (5, 'dispatcher', 'dispatch_reader_1b', 'directname', 'writeeventstore');
INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (6, 'dispatcher', 'dispatch_reader_1b', 'topic', 'test_topic');

INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (7, 'dispatcher', 'dispatch_reader_2a', 'templateId', 'ftp_reader_v1');
       INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (8, 'dispatcher', 'dispatch_reader_2a', 'directName', 'logger');
INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (9, 'dispatcher', 'dispatch_reader_2a', 'user', 'anonymous');
INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (10, 'dispatcher', 'dispatch_reader_2a', 'password', '1234');
INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (11, 'dispatcher', 'dispatch_reader_2a', 'server', 'localhost');
INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (12, 'dispatcher', 'dispatch_reader_2a', 'port', '2021');
INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (13, 'dispatcher', 'dispatch_reader_2a', 'directory', 'pub');
INSERT INTO route_template_params (id, profile, route_id, template_param_name, template_param_value)
       VALUES (14, 'dispatcher', 'dispatch_reader_2a', 'fileName', 'test.txt');

INSERT INTO routes (id, profile, route_id, route)
       VALUES (1, 'dispatcher', 'directlogger', '<route id="directlogger"><from uri="direct:logger"/><log message="Direct Logger Table: ${body}"/></route>');

INSERT INTO gwh_configs (config_name, config_key, config_value) VALUES ('dispatcher', 'test', 'test');
INSERT INTO gwh_configs (config_name, config_key, config_value) VALUES ('dispatcher', 'gwh.local.jms.server.broker', 'tcp://localhost:61617');
INSERT INTO gwh_configs (config_name, config_key, config_value) VALUES ('dispatcher', 'management.server.port', '8086');

INSERT INTO properties (id, profile, region, property_key, property_value) VALUES (1, 'dispatcher', 'local', 'test', 'test');
INSERT INTO properties (id, profile, region, property_key, property_value) VALUES (2, 'dispatcher', 'local', 'gwh.local.jms.server.broker', 'tcp://localhost:61617');
INSERT INTO properties (id, profile, region, property_key, property_value) VALUES (3, 'dispatcher', 'local', 'management.server.port', '8086');