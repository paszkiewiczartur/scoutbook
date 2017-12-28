ALTER DATABASE `scoutbook` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `events` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `groups` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `new_password_code` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `post` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `user` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `user_events` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `user_friends` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `user_groups` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `user_profile` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `user_role` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `user_roles` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
ALTER TABLE `user_wall` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
insert into user_role(role, description) VALUES("ROLE_USER", "default role for user");
insert into user_role(role, description) VALUES("ROLE_ADMIN", "default role for admin");
insert into user_profile(firstname, lastname, gender, birthday, profile_image, background_image) VALUES("Michał", "Wiśniewski", 1, '1990-01-03', "https://www.baldingbeards.com/wp-content/uploads/2017/03/Johhny-Depp-Jack-Sparrow-Beard.png", "https://pcavote.files.wordpress.com/2016/12/pirates-jack-sparrow.jpg?w=945");
insert into user(email, password, user_profile_id) VALUES("test", "test", 1);
insert into user_roles(user_id, roles_id) VALUES(1, 1);
insert into user_profile(firstname, lastname, gender, birthday, profile_image, background_image) VALUES("ZHP", "hufiec Białystok", 1, '1970-01-01', "http://kolno.zhp.pl/wp-content/uploads/2014/09/zhplogo.png", "http://mikolow.zhp.pl/img/logozhp.png");
insert into user(email, password, user_profile_id) VALUES("bialystok@zhp.pl", "test", 2);
insert into user_roles(user_id, roles_id) VALUES(2, 1);
insert into user_profile(firstname, lastname, gender, birthday, profile_image, background_image) VALUES("Scout Association", "London", 1, '1970-01-01', "https://cdn.pngworkforce.com/img/jobpage/pngwf-the-scout-association-of-papua-new-guinea-sapng-99933412.png", "https://upload.wikimedia.org/wikipedia/en/thumb/d/de/The_Scout_Association_logo.svg/1200px-The_Scout_Association_logo.svg.png");
insert into user(email, password, user_profile_id) VALUES("london@scout.uk", "test", 3);
insert into user_roles(user_id, roles_id) VALUES(3, 1);
insert into user_profile(firstname, lastname, gender, birthday, profile_image, background_image) VALUES("Marcin", "Chodakowski", 1, '1975-01-01', "http://www.mbc.net/default/mediaObject/Photos/2013/september/20-9-2013/mbc2/SWEENEY-TODD-THE-DEMON-BARBER-OF-FLEET-STREET_1024/original/2d4a98d533d41519afe1bcdbcf0eaf0b3a9b940b/SWEENEY-TODD-THE-DEMON-BARBER-OF-FLEET-STREET_1024.jpg", "http://moviesroom.pl/images/0.Aktualizacja_listopad/Pat/sweeney-todd-the-demon-barber-of-fleet-street-4fdb7ba3576e8-1000x600.jpg");
insert into user(email, password, user_profile_id) VALUES("test2", "test2", 4);
insert into user_roles(user_id, roles_id) VALUES(4, 1);
insert into user_friends(user_profile_id, user_friend_id) VALUES(1, 4);
insert into user_friends(user_profile_id, user_friend_id) VALUES(4, 1);
insert into user_friends(user_profile_id, user_friend_id) VALUES(2, 4);
insert into user_friends(user_profile_id, user_friend_id) VALUES(4, 2);
insert into user_friends(user_profile_id, user_friend_id) VALUES(1, 2);
insert into user_friends(user_profile_id, user_friend_id) VALUES(2, 1);
insert into user_friends(user_profile_id, user_friend_id) VALUES(4, 3);
insert into user_friends(user_profile_id, user_friend_id) VALUES(3, 4);

insert into groups(name, image) VALUES("hufiec Białystok", "https://zgloszenia24.pl/uploads/users/143/5847b9aa966d7419ad90658f894f07e3.jpg");
insert into post(content, created_at, groups_id, owner_id, category) VALUES("To jest nasz pierwszy post", "2017-12-01 12:11:10", 1, 2, 1);
insert into post(content, created_at, groups_id, owner_id, category) VALUES("To jest nasz drugi post", "2017-12-01 13:12:11", 1, 2, 1);
insert into post(content, created_at, groups_id, owner_id, category) VALUES("Cześć! Jestem nowy w drużynie.", "2017-12-01 13:12:11", 1, 1, 1);
insert into post(content, created_at, user_profile_id, owner_id, category) VALUES("Kto ma do pożyczenia czołówkę?", "2017-12-03 13:12:11", 1, 1, 0);
insert into post(content, created_at, user_profile_id, owner_id, category) VALUES("Zna ktoś ciekawe miejsce na złożenie przysięgi?", "2017-12-03 13:12:11", 1, 1, 0);
insert into post(content, created_at, user_profile_id, owner_id, category) VALUES("Wszystkiego najlepszego z okazji urodzin!", "2017-11-03 13:12:11", 1, 2, 0);
insert into post(content, created_at, user_profile_id, owner_id, category) VALUES("Stówa Michał!", "2017-11-03 9:10:11", 1, 4, 0);
insert into groups(name) VALUES("scout group London");
insert into post(content, created_at, groups_id, owner_id, category) VALUES("This is our first post", "2017-12-02 14:13:12", 2, 3, 1);
insert into post(content, created_at, groups_id, owner_id, category) VALUES("This is our second post", "2017-12-02 15:14:13", 2, 3, 1);
insert into user_groups(user_profile_id, groups_id) VALUES(1, 1);
insert into user_groups(user_profile_id, groups_id) VALUES(1, 2);
insert into user_groups(user_profile_id, groups_id) VALUES(2, 1);
insert into user_groups(user_profile_id, groups_id) VALUES(3, 2);
insert into user_groups(user_profile_id, groups_id) VALUES(4, 1);
insert into user_wall(user_profile_id, post_id, shown) VALUES(1, 1, false);
insert into user_wall(user_profile_id, post_id, shown) VALUES(1, 2, false);
insert into user_wall(user_profile_id, post_id, shown) VALUES(1, 3, false);
insert into user_wall(user_profile_id, post_id, shown) VALUES(1, 4, false);
insert into user_wall(user_profile_id, post_id, shown) VALUES(1, 5, false);
insert into user_wall(user_profile_id, post_id, shown) VALUES(1, 6, false);
insert into user_wall(user_profile_id, post_id, shown) VALUES(1, 7, false);
insert into events(organizer_id, name, place, start, end, info) VALUES(2, "Światełko do nieba", "Białystok", "2017-12-17 12:00:00", "2017-12-17 13:00:00", "Zapraszamy wszystkich!");
insert into user_events(user_profile_id, events_id) VALUES(2, 1);
insert into user_events(user_profile_id, events_id) VALUES(1, 1);
insert into user_events(user_profile_id, events_id) VALUES(4, 1);
insert into post(content, created_at, events_id, owner_id, category) VALUES("Proszę być punktualnie.", "2017-12-01 13:12:11", 1, 2, 2);
insert into post(content, created_at, events_id, owner_id, category) VALUES("Czy mogę przyjść z rodzicami?", "2017-12-01 13:12:11", 1, 1, 2);
