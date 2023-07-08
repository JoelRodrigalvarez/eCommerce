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
INSERT INTO PRODUCT (description, price) VALUES ('Producto 1', 10.50);
INSERT INTO PRODUCT (description, price) VALUES ('Producto 2', 15.75);
INSERT INTO PRODUCT (description, price) VALUES ('Producto 3', 5.99);
