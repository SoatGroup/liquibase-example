-- Insert data on table POST
INSERT INTO post(id, author, content, title) VALUES (nextval('post_id_seq'), 'test', 'content-1', 'title-1');
INSERT INTO post(id, author, content, title) VALUES (nextval('post_id_seq'), 'test', 'content-2', 'title-2');
INSERT INTO post(id, author, content, title) VALUES (nextval('post_id_seq'), 'test', 'content-3', 'title-3');
INSERT INTO post(id, author, content, title) VALUES (nextval('post_id_seq'), 'test', 'content-4', 'title-4');

-- Insert data on table COMMENT
INSERT INTO comment(id, author, content, post_id) VALUES (nextval('comment_id_seq'), 'test', 'content-1', select id from post where title = 'title-1');
INSERT INTO comment(id, author, content, post_id) VALUES (nextval('comment_id_seq'), 'test', 'content-2', select id from post where title = 'title-1');
INSERT INTO comment(id, author, content, post_id) VALUES (nextval('comment_id_seq'), 'test', 'content-3', select id from post where title = 'title-2');
INSERT INTO comment(id, author, content, post_id) VALUES (nextval('comment_id_seq'), 'test', 'content-4', select id from post where title = 'title-3');
