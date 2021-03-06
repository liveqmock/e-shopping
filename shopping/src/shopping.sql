DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    user_id INT(10) NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(32) NOT NULL,
    user_password VARCHAR(32) NOT NULL,
    gender TINYINT(1) NOT NULL DEFAULT 0,
    grade TINYINT(1) NOT NULL DEFAULT 0,
    balance FLOAT(10) NOT NULL DEFAULT 0.0,
    prompt VARCHAR(50),
    answer VARCHAR(50),
    favor VARCHAR(100),
    payed FLOAT(10) NOT NULL DEFAULT 0.0,
    user_state TINYINT(1) NOT NULL DEFAULT 0,
    reg_datetime DATETIME NOT NULL,
    email VARCHAR(50),
    phone VARCHAR(20),
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS category;
CREATE TABLE category
(
    cat_id INT(10) NOT NULL AUTO_INCREMENT,
    cat_name VARCHAR(32) NOT NULL,
    cat_desc MEDIUMTEXT NOT NULL,
    cat_datetime DATETIME NOT NULL,
    PRIMARY KEY (cat_id)
);

DROP TABLE IF EXISTS item;
CREATE TABLE item
(
    item_id INT(10) NOT NULL AUTO_INCREMENT,
    cat_id INT(10) NOT NULL,
    item_name VARCHAR(32) NOT NULL,
    item_desc MEDIUMTEXT NOT NULL,
    item_datetime DATETIME NOT NULL,
    PRIMARY KEY (item_id)
);

DROP TABLE IF EXISTS product;
CREATE TABLE product
(
    pro_id INT(10) NOT NULL AUTO_INCREMENT,
    item_id INT(10) NOT NULL,
    pro_name VARCHAR(32) NOT NULL,
    imageurl VARCHAR(50) NOT NULL,
    pro_desc MEDIUMTEXT NOT NULL,
    pro_datetime DATETIME NOT NULL,
    pur_price FLOAT(10) NOT NULL,
    ori_price FLOAT(10) NOT NULL,
    dis_price FLOAT(10) NOT NULL,
    stock INT(10) NOT NULL DEFAULT 0,
    sales INT(10) NOT NULL DEFAULT 0,
    recommendation TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (pro_id)
);

DROP TABLE IF EXISTS myorder;
CREATE TABLE myorder
(
    order_id INT(10) NOT NULL AUTO_INCREMENT,
    user_id INT(10) NOT NULL,
	con_id INT(10),
    order_num VARCHAR(32) NOT NULL,
    order_datetime DATETIME NOT NULL,
    payment TINYINT(1) NOT NULL DEFAULT 0,
    post TINYINT(1) NOT NULL DEFAULT 0,
    total_price FLOAT(10) NOT NULL,
    order_state TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (order_id)
);

DROP TABLE IF EXISTS orderinfo;
CREATE TABLE orderinfo
(
    info_id INT(10) NOT NULL AUTO_INCREMENT,
    order_id INT(10) NOT NULL,
    pro_id INT(10) NOT NULL,
    amount INT(10) NOT NULL,
    price FLOAT(10) NOT NULL,
    PRIMARY KEY (info_id)
);

DROP TABLE IF EXISTS contact;
CREATE TABLE contact
(
    con_id INT(10) NOT NULL AUTO_INCREMENT,
    order_id INT(10) NOT NULL,
    name VARCHAR(32) NOT NULL,
    postcode VARCHAR(10) NOT NULL,
    address VARCHAR(100) NOT NULL,
    telphone VARCHAR(20) NOT NULL,
    freetime TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (con_id)
);

DROP TABLE IF EXISTS admin;
CREATE TABLE admin
(
    admin_id INT(10) NOT NULL AUTO_INCREMENT,
    admin_name VARCHAR(32) NOT NULL,
    admin_password VARCHAR(32) NOT NULL,
    admin_datetime DATETIME NOT NULL,
    PRIMARY KEY (admin_id)
);

DROP TABLE IF EXISTS cart;
CREATE TABLE cart
(
    cart_id INT(10) NOT NULL AUTO_INCREMENT,
    user_id INT(10) NOT NULL,
    pro_id INT(10) NOT NULL,
    pro_amount INT(10) NOT NULL,
    add_datetime DATETIME NOT NULL,
    PRIMARY KEY (cart_id)
);

DROP TABLE IF EXISTS card;
CREATE TABLE card
(
	card_id INT(10) NOT NULL AUTO_INCREMENT,
	card_no VARCHAR(32) NOT NULL,
	card_password VARCHAR(32) NOT NULL,
	card_value FLOAT(10) NOT NULL,
	card_datetime DATETIME NOT NULL,
	card_flag TINYINT(1) NOT NULL DEFAULT 0,
	PRIMARY KEY (card_id)
);

ALTER TABLE item ADD CONSTRAINT FK_1 FOREIGN KEY(cat_id) REFERENCES category(cat_id);

ALTER TABLE product ADD CONSTRAINT FK_2 FOREIGN KEY(item_id) REFERENCES item(item_id);

ALTER TABLE myorder ADD CONSTRAINT FK_3 FOREIGN KEY(user_id) REFERENCES user(user_id);

ALTER TABLE myorder ADD CONSTRAINT FK_4 FOREIGN KEY(con_id) REFERENCES contact(con_id);

ALTER TABLE orderinfo ADD CONSTRAINT FK_5 FOREIGN KEY(order_id) REFERENCES myorder(order_id);

ALTER TABLE orderinfo ADD CONSTRAINT FK_6 FOREIGN KEY(pro_id) REFERENCES product(pro_id);

ALTER TABLE contact ADD CONSTRAINT FK_7 FOREIGN KEY(order_id) REFERENCES myorder(order_id);

ALTER TABLE cart ADD CONSTRAINT FK_8 FOREIGN KEY(user_id) REFERENCES user(user_id);

ALTER TABLE cart ADD CONSTRAINT FK_9 FOREIGN KEY(pro_id) REFERENCES product(pro_id);

-- ALTER TABLE myorder DROP FOREIGN KEY FK_4;
ALTER TABLE product ADD product_flag TINYINT(1) NOT NULL DEFAULT 0;

