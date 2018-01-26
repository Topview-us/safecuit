DROP TABLE IF EXISTS organization;
CREATE TABLE organization (
  org_id      INT(11)      NOT NULL      AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(50)  NOT NULL,
  address     VARCHAR(255) NOT NULL,
  area_id     INT(20)      NOT NULL,
  manager_id  INT(11)      NOT NULL,
  email       VARCHAR(255) NOT NULL,
  phone       VARCHAR(20)  NOT NULL,
  description VARCHAR(255) NOT NULL,
  del_tag     INT(1)       NOT NULL
);

DROP TABLE IF EXISTS s_user;
CREATE TABLE s_user (
  user_id     INT(11)      NOT NULL      AUTO_INCREMENT PRIMARY KEY,
  username    VARCHAR(50)  NOT NULL,
  real_name   VARCHAR(255) NOT NULL,
  password    VARCHAR(255) NOT NULL,
  org_id      INT(11)      NOT NULL,
  phone       VARCHAR(20)  NULL,
  qq          INT(15)      NULL,
  description VARCHAR(255) NULL,
  del_tag     INT(1)       NOT NULL DEFAULT 0
);