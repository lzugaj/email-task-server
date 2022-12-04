#!/usr/bin/env bash
psql "postgres://$POSTGRES_USER:$POSTGRES_PASSWORD@$POSTGRES_HOST/$POSTGRES_DB?sslmode=disable" <<-EOSQL
  SELECT 'CREATE DATABASE "email-task"'
  WHERE NOT EXISTS (SELECT FROM postgres WHERE datname = 'email-task')\gexec
EOSQL
