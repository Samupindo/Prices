CREATE TABLE products(
    product_id SERIAL primary key,
    name varchar(30) not null
);

CREATE TABLE shops(
    shop_id SERIAL primary key,
    country varchar(50) not null,
    city varchar(50) not null,
    address varchar(50) not null
);

CREATE TABLE product_in_Shop(
	product_in_Shop_id SERIAL primary key,
    product_id Integer not null,
    shop_id Integer not null,
    price Numeric(10,2) not null,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (shop_id) REFERENCES shops(shop_id) ON DELETE CASCADE
);

create table customers(
	customer_id SERIAL primary key,
	name varchar(100) not null,
	email varchar(100) unique not null,
	phone INT
);

create table purchases(
	purchase_id SERIAL primary key,
	customer_id INT not null references customers(customer_id) on delete cascade,
	shopping BOOLEAN
);


CREATE TABLE purchase_line (
    purchase_line_id SERIAL PRIMARY KEY NOT NULL,
    purchase_id INT NOT NULL,
    product_in_shop_id INT NOT NULL,
    FOREIGN KEY (purchase_id) REFERENCES purchases(purchase_id) ON DELETE CASCADE,
    FOREIGN KEY (product_in_shop_id) REFERENCES product_in_Shop(product_in_shop_id) ON DELETE CASCADE
);