DROP TABLE IF EXISTS user_order_item;
DROP TABLE IF EXISTS user_order;
DROP TABLE IF EXISTS user_message;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS menu_item;
DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS product;

CREATE TABLE user (
  user_id  INT PRIMARY KEY  NOT NULL AUTO_INCREMENT,
  name     VARCHAR(100)     NOT NULL,
  password VARCHAR(100)     NOT NULL,
  role     ENUM('USER', 'ADMIN', 'SUPER_ADMIN')
);

CREATE TABLE user_order (
  user_order_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id       INT,
  paid          BOOLEAN DEFAULT FALSE,
  status        BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (user_id) REFERENCES user (user_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE product (
  product_id  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name        VARCHAR(100)    NOT NULL,
  description VARCHAR(100)    NOT NULL
);

CREATE TABLE menu (
  menu_id           INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  first_day_of_week DATE,
  status            BOOLEAN DEFAULT FALSE
);

CREATE TABLE menu_item (
  menu_item_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  product_id   INT,
  day          INT             NOT NULL,
  rank         INT             NOT NULL,
  menu_id      INT,
  price        INT             NOT NULL,
  FOREIGN KEY (product_id) REFERENCES product (product_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (menu_id) REFERENCES menu (menu_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE user_order_item (
  menu_item_id  INT,
  user_order_id INT,
  FOREIGN KEY (menu_item_id) REFERENCES menu_item (menu_item_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (user_order_id) REFERENCES user_order (user_order_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE message (
  message_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT ,
  message VARCHAR (300) NOT NULL
);

CREATE  TABLE user_message (
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT ,
  from_user_id INT,
  to_user_id INT,
  message_id INT,
  read_status BOOL DEFAULT FALSE  NOT NULL,
  message_type ENUM('FROM_USER', 'FROM_ADMIN'),
  FOREIGN KEY (from_user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (to_user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (message_id) REFERENCES message (message_id) ON DELETE CASCADE ON UPDATE CASCADE
);
