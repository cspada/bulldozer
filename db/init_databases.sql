
CREATE USER bulldozer WITH PASSWORD 'bulldozer';

create database bar OWNER bulldozer;
create database inventory OWNER bulldozer;
create database go_home OWNER bulldozer;

\c bar
GRANT ALL ON SCHEMA public TO bulldozer;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO bulldozer;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO bulldozer;

\c inventory
GRANT ALL ON SCHEMA public TO bulldozer;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO bulldozer;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO bulldozer;

\c go_home
GRANT ALL ON SCHEMA public TO bulldozer;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO bulldozer;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO bulldozer;