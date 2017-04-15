DROP TABLE diff_document;

CREATE TABLE diff_document(
    id int  PRIMARY KEY NOT NULL,
    left varchar,
    right varchar,
    diff varchar,
    lengthLeft int,
    lengthRight int
);

INSERT INTO diff_document(id, left, right) VALUES (1, 'A', 'B');