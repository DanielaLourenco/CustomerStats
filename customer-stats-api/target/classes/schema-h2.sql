CREATE TABLE CUSTOMER (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  active tinyint(1) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (id)
);      
        
CREATE TABLE IP_BLACKLIST (
  ip varchar(45) NOT NULL,
  PRIMARY KEY (ip)
);

CREATE TABLE UA_BLACKLIST (
  ua varchar(255) NOT NULL,
  PRIMARY KEY (ua)
);

CREATE TABLE HOURLY_STATS (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  customer_id int(11) unsigned NOT NULL,
  time timestamp NOT NULL,
  request_count bigint(20) unsigned NOT NULL DEFAULT 0,
  invalid_count bigint(20) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE KEY unique_customer_time (customer_id,time),
  CONSTRAINT hourly_stats_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE ON UPDATE NO ACTION
);
