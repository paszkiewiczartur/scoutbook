insert into user_role(role, description) VALUES ("ROLE_USER", "default role for user");
insert into user_role(role, description) VALUES ("ROLE_ADMIN", "default role for admin");
insert into user(email, password) VALUES ("test@wp.pl", "test");
insert into user_roles(user_id, roles_id) VALUES(1, 1);