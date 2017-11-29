insert into user_role(role, description) VALUES ("ROLE_USER", "default role for user");
insert into user_role(role, description) VALUES ("ROLE_ADMIN", "default role for admin");
insert into user_profile(firstname, lastname, gender, birthday) VALUES ("Michał", "Wiśniewski", 0, '1990-01-03');
insert into user(email, password, user_profile_id) VALUES ("atawars@gmail.com", "hasło", 1);
insert into user_roles(user_id, roles_id) VALUES(1, 1);