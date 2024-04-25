-- Script to insert dummy dev data into the database.

-- You first need to register two users into the system before running this script.

-- Replace the id here with the first user id you want to have ownership of the orders.
  DO $$
        DECLARE userId1 INT := 1;
-- Replace the id here with the second user id you want to have ownership of the orders.
        DECLARE userId2 INT := 2;
    BEGIN
        DELETE FROM web_order_quantities;
        DELETE FROM web_order;
        DELETE FROM inventory;
        DELETE FROM product;
        DELETE FROM address;

        INSERT INTO product (name, short_description, long_description, price) VALUES ('Product #1', 'Product one short description.', 'This is a very long description of product #1.', 5.50);
        INSERT INTO product (name, short_description, long_description, price) VALUES ('Product #2', 'Product two short description.', 'This is a very long description of product #2.', 10.56);
        INSERT INTO product (name, short_description, long_description, price) VALUES ('Product #3', 'Product three short description.', 'This is a very long description of product #3.', 2.74);
        INSERT INTO product (name, short_description, long_description, price) VALUES ('Product #4', 'Product four short description.', 'This is a very long description of product #4.', 15.69);
        INSERT INTO product (name, short_description, long_description, price) VALUES ('Product #5', 'Product five short description.', 'This is a very long description of product #5.', 42.59);

        INSERT INTO inventory (product_id, quantity) VALUES ((SELECT id FROM product WHERE name = 'Product #1'), 5);
        INSERT INTO inventory (product_id, quantity) VALUES ((SELECT id FROM product WHERE name = 'Product #2'), 8);
        INSERT INTO inventory (product_id, quantity) VALUES ((SELECT id FROM product WHERE name = 'Product #3'), 12);
        INSERT INTO inventory (product_id, quantity) VALUES ((SELECT id FROM product WHERE name = 'Product #4'), 73);
        INSERT INTO inventory (product_id, quantity) VALUES ((SELECT id FROM product WHERE name = 'Product #5'), 2);

        INSERT INTO address (address_line_1, city, country, user_id) VALUES ('123 Tester Hill', 'Testerton', 'England', userId1);
        INSERT INTO address (address_line_1, city, country, user_id) VALUES ('312 Spring Boot', 'Hibernate', 'England', userId2);

        INSERT INTO web_order (address_id, user_id) VALUES ((SELECT id FROM address WHERE user_id = userId1 ORDER BY id DESC LIMIT 1), userId1);
        INSERT INTO web_order (address_id, user_id) VALUES ((SELECT id FROM address WHERE user_id = userId1 ORDER BY id DESC LIMIT 1), userId1);
        INSERT INTO web_order (address_id, user_id) VALUES ((SELECT id FROM address WHERE user_id = userId1 ORDER BY id DESC LIMIT 1), userId1);
        INSERT INTO web_order (address_id, user_id) VALUES ((SELECT id FROM address WHERE user_id = userId2 ORDER BY id DESC LIMIT 1), userId2);
        INSERT INTO web_order (address_id, user_id) VALUES ((SELECT id FROM address WHERE user_id = userId2 ORDER BY id DESC LIMIT 1), userId2);

        INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES ((SELECT id FROM web_order WHERE address_id = (SELECT id FROM address WHERE user_id = userId1 ORDER BY id DESC LIMIT 1) AND user_id = userId1 ORDER BY id DESC LIMIT 1), (SELECT id FROM product WHERE name = 'Product #1'), 5);
        INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES ((SELECT id FROM web_order WHERE address_id = (SELECT id FROM address WHERE user_id = userId1 ORDER BY id DESC LIMIT 1) AND user_id = userId1 ORDER BY id DESC LIMIT 1), (SELECT id FROM product WHERE name = 'Product #2'), 5);
        INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES ((SELECT id FROM web_order WHERE address_id = (SELECT id FROM address WHERE user_id = userId1 ORDER BY id DESC LIMIT 1) AND user_id = userId1 ORDER BY id DESC LIMIT 1), (SELECT id FROM product WHERE name = 'Product #3'), 5);
        INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES ((SELECT id FROM web_order WHERE address_id = (SELECT id FROM address WHERE user_id = userId2 ORDER BY id DESC LIMIT 1) AND user_id = userId2 ORDER BY id DESC LIMIT 1), (SELECT id FROM product WHERE name = 'Product #4'), 5);
        INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES ((SELECT id FROM web_order WHERE address_id = (SELECT id FROM address WHERE user_id = userId2 ORDER BY id DESC LIMIT 1) AND user_id = userId2 ORDER BY id DESC LIMIT 1), (SELECT id FROM product WHERE name = 'Product #3'), 5);
    END $$;