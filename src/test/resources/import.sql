INSERT INTO t_authority(name) VALUES ('ROLE_USER');
INSERT INTO t_authority(name) VALUES ('ROLE_ADMIN');

INSERT INTO t_user (login, password, first_name, last_name, email, activated, activation_key, created_by, created_date, last_modified_by, last_modified_date) VALUES ('jntakpe', '$2a$10$I7jK3us6BhjLWFmOFOdgKeikb5HUM.mIWOltv4RLNOzKJOJQj2eva', 'Jocelyn', 'Ntakpé', 'jntakpe@gmail.com', true, null, 'system', '2015-02-05 21:06:22.686449', null, null);

INSERT INTO t_user_authority(user_id, authority_name) SELECT t_user.id, t_authority.name FROM t_user, t_authority WHERE t_user.login = 'jntakpe';