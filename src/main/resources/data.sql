CREATE TABLE CART (
  cart_id VARCHAR(255) PRIMARY KEY,
  total_price DECIMAL(10, 2)
);

CREATE TABLE PRODUCT (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    price DECIMAL(10, 2),
    cart_id VARCHAR(255),
    FOREIGN KEY (cart_id) REFERENCES CART(cart_id)
);

-- Inserts para la tabla PRODUCT
INSERT INTO PRODUCT (id,description, price) VALUES (1,'Producto 1', 10.50);
INSERT INTO PRODUCT (id,description, price) VALUES (2,'Producto 2', 15.75);
INSERT INTO PRODUCT (id,description, price) VALUES (3,'Producto 3', 5.99);
