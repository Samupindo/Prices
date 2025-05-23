INSERT INTO shops(country,city,address) values
( 'Argentina', 'Buenos Aires',  'Alfredo R. Bufano 2701-2799'),
('Argentina', 'Córdoba', 'Av. Vélez Sarsfield'),
('España', 'A Coruña', 'Rúa Río Brexa, 5 '),
('Francia', 'Marsella', 'Grands-Carmes 13002 Marsella'),
('Portugal', 'Lisboa', 'Rua da Bempostinha 1');

INSERT INTO products(name) values
('Zumos'),
('Bebidas energéticas'),
('Agua'),
('Patatas'),
('Fruta');

INSERT INTO product_in_Shop(product_id, shop_id, price) VALUES
(1, 1, 7.9),
(2, 2, 8.9),
(3, 3, 3.9),
(4, 4, 5.5),
(5, 5, 3.2);

insert into customers (name, email, phone) values
('Alice Johnson', 'alice@example.com', 123123123),
('Bob Smith', 'bob@example.com', 111111111),
('Carlos Garcia', 'carlos@example.com', 999999999),
('Sara Lopez', 'sara@example.com',654654654);

insert into purchases (customer_id,shopping ) values
(1,true),
(4,true),
(2,true),
(3,true)

insert into purchase_line (purchase_id, product_in_shop_id) values
('1','1'),
('2','2');