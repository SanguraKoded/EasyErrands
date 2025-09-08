CREATE DATABASE assignment_service;
CREATE DATABASE auth_service;
CREATE DATABASE errand_service;
CREATE DATABASE tracking_service;
CREATE USER keycloak WITH PASSWORD 'keycloakpass';
CREATE DATABASE keycloak;
GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak;

\connect keycloak;
GRANT ALL ON SCHEMA public TO keycloak;
ALTER SCHEMA public OWNER TO keycloak;



