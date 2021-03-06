INSERT INTO t_authority(name) VALUES ('ROLE_USER');
INSERT INTO t_authority(name) VALUES ('ROLE_ADMIN');

INSERT INTO t_user (login, password, first_name, last_name, email, activated, activation_key, created_by, created_date, last_modified_by, last_modified_date, version) VALUES ('jntakpe', '$2a$10$Zvnm2dNkCwhswN8xvXKkEuZEqCoghHKc7w2b0MKaXIPNfkSdfDumu', 'Jocelyn', 'Ntakpé', 'jntakpe@gmail.com', true, null, 'system', '2015-02-05 21:06:22.686449', null, null, 0);
INSERT INTO t_user (login, password, first_name, last_name, email, activated, activation_key, created_by, created_date, last_modified_by, last_modified_date, version) VALUES ('secret', '$2a$10$Zvnm2dNkCwhswN8xvXKkEuZEqCoghHKc7w2b0MKaXIPNfkSdfDumu', 'Secret', 'User', 'secret@gmail.com', true, null, 'system', '2015-02-05 21:06:22.686449', null, null, 0);

INSERT INTO t_user_authority(user_id, authority_name) SELECT t_user.id, t_authority.name FROM t_user, t_authority WHERE t_user.login = 'jntakpe';
INSERT INTO t_user_authority(user_id, authority_name) SELECT t_user.id, t_authority.name FROM t_user, t_authority WHERE t_user.login = 'secret' AND t_authority.name = 'ROLE_USER';