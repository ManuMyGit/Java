INSERT INTO Role (id, name) VALUES (1, 'STANDARD_USER');
INSERT INTO Role (id, name) VALUES (2, 'ADMIN_USER');

-- USER
-- non-encrypted password: jwtpass
INSERT INTO User (id, username, password, salary, age) VALUES (1, 'User1', '$2a$06$H7hBAj84mOpg9YVIJnQrHe1GrvYbe4eua19d9jeK56sgqtP9XT77.', 3456, 33);
INSERT INTO User (id, username, password, salary, age) VALUES (2, 'User2', '$2a$06$H7hBAj84mOpg9YVIJnQrHe1GrvYbe4eua19d9jeK56sgqtP9XT77.', 7823, 23);


INSERT INTO user_roles(user_id, roles_id) VALUES(1,2);
INSERT INTO user_roles(user_id, roles_id) VALUES(2,1);

-- Populate random city table
INSERT INTO City (id, name, country) VALUES (1, 'Seville', 'Spain');
INSERT INTO City (id, name, country) VALUES (2, 'London', 'UK');
INSERT INTO City (id, name, country) VALUES (3, 'Paris', 'France');
INSERT INTO City (id, name, country) VALUES (4, 'New York', 'USA');

-- Client
-- non-encrypted password: XY7kmzoNzl100
INSERT INTO oauth_client_details
    (id, client_id, resource_ids, client_secret,
    scope, authorized_grant_types,
    web_server_redirect_uri, authorities, access_token_validity,
    refresh_token_validity, additional_information, autoapprove)
VALUES
    (1, 'clientoauth2jwtpassword', 'resourceId1', '$2a$06$lqPwi/4gpoVg7fBdJmtLSejUK0FKCtEBzYzEy84z1CXOG.GU/f2ra',
    'read,write', 'password,refresh_token',
    null, null, 10, 
    600, null, true);
INSERT INTO oauth_client_details
    (id, client_id, resource_ids, client_secret,
    scope, authorized_grant_types,
    web_server_redirect_uri, authorities, access_token_validity,
    refresh_token_validity, additional_information, autoapprove)
VALUES
    (2, 'clientoauth2jwtclient', 'resourceId1', '$2a$06$lqPwi/4gpoVg7fBdJmtLSejUK0FKCtEBzYzEy84z1CXOG.GU/f2ra',
    'read,write', 'client_credentials',
    null, 'ADMIN_USER', 100, 
    null, null, true);