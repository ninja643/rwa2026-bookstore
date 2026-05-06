INSERT INTO permissions (name, description)
VALUES ('users.read_all',              'Read all users'),
       ('users.password.change_self',  'Change own password'),
       ('users.password.change_other', 'Change another user password'),
       ('users.create',                'Create users'),
       ('users.modify',                'Modify users'),
       ('users.delete',                'Delete users'),
       ('audit.read',                  'Read audit logs');

INSERT INTO roles (name)
VALUES ('ADMIN'),
       ('EDITOR'),
       ('USER');

-- ADMIN: all permissions
INSERT INTO roles_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         JOIN permissions p
WHERE r.name = 'ADMIN'
  AND p.name IN (
    'users.read_all',
    'users.password.change_self',
    'users.password.change_other',
    'users.create',
    'users.modify',
    'users.delete',
    'audit.read'
);

-- EDITOR: change own password only
INSERT INTO roles_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         JOIN permissions p
WHERE r.name = 'EDITOR'
  AND p.name = 'users.password.change_self';

-- USER: change own password only
INSERT INTO roles_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
         JOIN permissions p
WHERE r.name = 'USER'
  AND p.name = 'users.password.change_self';
