-- TODO: Improve the init scripts

-- CREATE USER food_ordering_user WITH PASSWORD 'yourpassword';

--DROP DATABASE IF EXISTS food_ordering_db;

-- CREATE DATABASE food_ordering_db;
-- GRANT ALL PRIVILEGES ON DATABASE food_ordering_db TO food_ordering_user;
--
-- connect food_ordering_db;


DROP SCHEMA IF EXISTS "order" CASCADE;

CREATE SCHEMA "order";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS order_status;
CREATE TYPE order_status AS ENUM ('PENDING', 'PAID', 'APPROVED', 'CANCELLED', 'CANCELLING');

DROP TABLE IF EXISTS "order".orders CASCADE;

CREATE TABLE "order".orders
(
    id uuid NOT NULL,
    customer_id uuid NOT NULL,
    restaurant_id uuid NOT NULL,
    tracking_id uuid NOT NULL,
    price numeric (10,2) NOT NULL,
    order_status order_status NOT NULL,
    failure_messages character varying COLLATE pg_catalog."default",
    CONSTRAINT orders_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "order".order_items CASCADE;

CREATE TABLE "order".order_items
(
    id BIGINT NOT NULL,
    order_id uuid NOT NULL,
    product_id uuid NOT NULL,
    price numeric (10,2) NOT NULL,
    quantity INTEGER NOT NULL,
    sub_total numeric (10,2) NOT NULL,
    CONSTRAINT order_items_pkey PRIMARY KEY (id, order_id)
);

ALTER TABLE "order".order_items
    ADD CONSTRAINT "FK_ORDER_ID" FOREIGN KEY (order_id)
    REFERENCES "order".orders (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    NOT VALID;

DROP TABLE IF EXISTS "order".order_address CASCADE;

CREATE TABLE "order".order_address
(
    id UUID NOT NULL,
    order_id uuid NOT NULL,
    street character varying COLLATE pg_catalog."default" NOT NULL,
    postal_code character varying COLLATE pg_catalog."default" NOT NULL,
    city character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT order_address_pkey PRIMARY KEY (id, order_id)
);

ALTER TABLE "order".order_address
    ADD CONSTRAINT "FK_ORDER_ID" FOREIGN KEY (order_id)
    REFERENCES "order".orders (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE
    NOT VALID;


GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA "order" TO food_ordering_user;
