-- This script sets up some tables in PGDQL DB in a local docker container (see docker-compose-kafka-only.yml)

-- SCHEMA CREATION

CREATE SCHEMA IF NOT EXISTS "college" AUTHORIZATION "antenna";

-- TABLES CREATION

CREATE TABLE IF NOT EXISTS "college"."user" (
   "id" bigserial NOT NULL,
   "name" varchar(100) NOT NULL,
   "email" varchar(100) NOT NULL,
   "disabled" boolean NOT NULL default false,
   "modified" timestamptz default now()
   )
;
-- DATA INSERTS

insert into "college"."user"
("name", "email","disabled")
values
   -- LSports --
   ('wei', 'wei@gmail.com', true),
   ('sherry', 'sherry@gmail.com', false);

