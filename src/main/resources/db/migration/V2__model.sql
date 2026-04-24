-- AUTHORS
CREATE TABLE authors
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    biography  TEXT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- PUBLISHERS
CREATE TABLE publishers
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    city    VARCHAR(100),
    country VARCHAR(100)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- CATEGORIES
CREATE TABLE categories
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- BOOKS
CREATE TABLE books
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(255)   NOT NULL,
    isbn             VARCHAR(20) UNIQUE,
    publication_year INT,
    price            DECIMAL(10, 2) NOT NULL,
    stock_quantity   INT            NOT NULL,
    description      TEXT,
    publisher_id     BIGINT,

    created_at       TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_books_publisher FOREIGN KEY (publisher_id) REFERENCES publishers (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- BOOK_AUTHORS (M:N)
CREATE TABLE book_authors
(
    book_id   BIGINT,
    author_id BIGINT,

    PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_ba_book FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    CONSTRAINT fk_ba_author FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- BOOK_CATEGORIES (M:N)
CREATE TABLE book_categories
(
    book_id     BIGINT,
    category_id BIGINT,

    PRIMARY KEY (book_id, category_id),
    CONSTRAINT fk_bc_book FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    CONSTRAINT fk_bc_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- BOOK_IMAGES
CREATE TABLE book_images
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id    BIGINT       NOT NULL,
    url        VARCHAR(500) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_book_images FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- INVENTORY_LOGS
CREATE TABLE inventory_logs
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id       BIGINT    NOT NULL,
    change_amount INT       NOT NULL,
    reason        VARCHAR(255),
    change_date   TIMESTAMP NOT NULL,

    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_inventory_logs_book FOREIGN KEY (book_id) REFERENCES books (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- CARTS
CREATE TABLE carts
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_carts_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- CART_ITEMS
CREATE TABLE cart_items
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id  BIGINT NOT NULL,
    book_id  BIGINT NOT NULL,
    quantity INT    NOT NULL,

    CONSTRAINT fk_cart_items_cart FOREIGN KEY (cart_id) REFERENCES carts (id) ON DELETE CASCADE,
    CONSTRAINT fk_cart_items_book FOREIGN KEY (book_id) REFERENCES books (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ADDRESSES
CREATE TABLE addresses
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    street      VARCHAR(255),
    city        VARCHAR(100),
    postal_code VARCHAR(20),
    country     VARCHAR(100)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ORDERS
CREATE TABLE orders
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT   NOT NULL,
    address_id  BIGINT   NOT NULL,
    order_date  TIMESTAMP NOT NULL,
    status      VARCHAR(50),
    total_price DECIMAL(10, 2),

    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_orders_address FOREIGN KEY (address_id) REFERENCES addresses (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ORDER_ITEMS
CREATE TABLE order_items
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id          BIGINT         NOT NULL,
    book_id           BIGINT         NOT NULL,
    quantity          INT            NOT NULL,
    price_at_purchase DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_oi_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_oi_book FOREIGN KEY (book_id) REFERENCES books (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- PAYMENTS
CREATE TABLE payments
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id       BIGINT   NOT NULL,
    payment_method VARCHAR(50),
    status         VARCHAR(50),
    payment_date   TIMESTAMP,
    amount         DECIMAL(10, 2),

    CONSTRAINT fk_payments_order FOREIGN KEY (order_id) REFERENCES orders (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- COUPONS
CREATE TABLE coupons
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    code           VARCHAR(50) UNIQUE,
    discount_value DECIMAL(10, 2),
    discount_type  VARCHAR(20),
    valid_from     TIMESTAMP,
    valid_to       TIMESTAMP
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- ORDER_COUPONS (M:N)
CREATE TABLE order_coupons
(
    order_id   BIGINT,
    coupon_id  BIGINT,

    PRIMARY KEY (order_id, coupon_id),
    CONSTRAINT fk_oc_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_oc_coupon FOREIGN KEY (coupon_id) REFERENCES coupons (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- REVIEWS
CREATE TABLE reviews
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT   NOT NULL,
    book_id     BIGINT   NOT NULL,
    rating      INT      NOT NULL,
    comment     TEXT,
    review_date TIMESTAMP,

    CONSTRAINT fk_reviews_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_reviews_book FOREIGN KEY (book_id) REFERENCES books (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
