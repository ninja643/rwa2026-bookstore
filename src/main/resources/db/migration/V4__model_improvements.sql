-- 1. Remove registration_date from users (redundant with created_at)
ALTER TABLE users DROP COLUMN registration_date;

-- 2. Remove order_date from orders (redundant with created_at)
ALTER TABLE orders DROP COLUMN order_date;

-- 3. Link addresses to users
ALTER TABLE addresses ADD COLUMN user_id BIGINT;
ALTER TABLE addresses
    ADD CONSTRAINT fk_addresses_user FOREIGN KEY (user_id) REFERENCES users (id);

-- 4. Hierarchical categories (parent → subcategory)
ALTER TABLE categories ADD COLUMN parent_id BIGINT;
ALTER TABLE categories
    ADD CONSTRAINT fk_category_parent FOREIGN KEY (parent_id) REFERENCES categories (id);

-- 5. Unique constraint: one review per user per book
ALTER TABLE reviews
    ADD CONSTRAINT uq_review_user_book UNIQUE (user_id, book_id);

-- 6. Unique constraint: one cart item entry per book in a cart
ALTER TABLE cart_items
    ADD CONSTRAINT uq_cart_item_book UNIQUE (cart_id, book_id);

-- 7. Payment amount must not be null
ALTER TABLE payments MODIFY COLUMN amount DECIMAL(10, 2) NOT NULL;

-- 8. Coupon: usage tracking and active flag
ALTER TABLE coupons ADD COLUMN usage_limit INT;
ALTER TABLE coupons ADD COLUMN usage_count INT NOT NULL DEFAULT 0;
ALTER TABLE coupons ADD COLUMN is_active  BOOLEAN NOT NULL DEFAULT TRUE;

-- 9. Remove updated_at and change_date from inventory_logs (logs are immutable)
ALTER TABLE inventory_logs DROP COLUMN updated_at;
ALTER TABLE inventory_logs DROP COLUMN change_date;

-- 10. Assign audit.read permission to ADMIN (was created but not assigned in V3)
INSERT INTO roles_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         JOIN permissions p
WHERE r.name = 'ADMIN'
  AND p.name = 'audit.read';
