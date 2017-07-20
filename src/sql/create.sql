-- Creates table for saving connection information

DROP TABLE IF EXISTS saved_connections;

CREATE TABLE saved_connections (
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    host    VARCHAR NOT NULL,
    name    VARCHAR NOT NULL,
    pass    VARCHAR NOT NULL,
    port    INTEGER
);

CREATE INDEX id_index
ON saved_connections (id);
