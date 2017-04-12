CREATE TABLE diff_document(
    id int  PRIMARY KEY NOT NULL,
    left varchar,
    right varchar
);

INSERT INTO diff_document(id, left, right) VALUES (1, 'A', 'B');