CREATE DATABASE assignment_service_db;
CREATE DATABASE auth_service_db;
CREATE DATABASE errand_service_db;
CREATE DATABASE tracking_service_db;
CREATE USER keycloak WITH PASSWORD 'keycloakpass';
CREATE DATABASE keycloak;
GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak;

\connect keycloak;
GRANT ALL ON SCHEMA public TO keycloak;
ALTER SCHEMA public OWNER TO keycloak;



