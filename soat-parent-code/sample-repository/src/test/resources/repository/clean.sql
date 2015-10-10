-- Drop all data stored in database
DELETE FROM comment;
DELETE FROM post;

-- Reset sequence
ALTER SEQUENCE comment_id_seq RESTART WITH 1;
ALTER SEQUENCE post_id_seq RESTART WITH 1;