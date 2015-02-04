-- Ajout des rôles
INSERT INTO t_authority(name) VALUES ('ROLE_USER');
INSERT INTO t_authority(name) VALUES ('ROLE_ADMIN');

-- Ajout de l'utilisateur 'jntakpe'
INSERT INTO t_user (id, created_by, created_date, last_modified_by, last_modified_date, version, activated, activation_key, email, first_name, last_name, login, password) VALUES (1, 'Anonymous', '2015-02-04', 'Anonymous', '2015-02-04 21:27:47.702000', 0, false, 'Qr3lMDfMDlzafPSKJs5n', 'j.ntakpe@gmail.com', 'Jocelyn', 'Ntakpé', 'jntakpe', '$2a$10$I7jK3us6BhjLWFmOFOdgKeikb5HUM.mIWOltv4RLNOzKJOJQj2eva');

-- Ajout des rôles USER et ADMIN à l'utilisateur 'jntakpe'
INSERT INTO t_user_authority(user_id, authority_name) SELECT t_user.id, t_authority.name FROM t_user, t_authority WHERE t_user.login = 'jntakpe';
