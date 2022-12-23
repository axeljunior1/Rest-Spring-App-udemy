INSERT INTO user_det (id, name, birthdate)
VALUES (100, 'Axel', current_date);

INSERT INTO post (id, user_id, `description`)
VALUES (10, 100, 'D1');