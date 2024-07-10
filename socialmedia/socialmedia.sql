use instagram;
select * from user;
alter table user add column is_active int default 0;
select * from likes;
select * from comments;
select * from messages order by id desc limit 1;
select * from posts;
select email,password from user where email="ajith@gmail.com" and password="ajith@123";
delete from messages where id=79; 
CREATE TABLE reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT NOT NULL, 
    reported_id INT NOT NULL,
    report_date DATETIME NOT NULL,
    reason VARCHAR(255),
    FOREIGN KEY (sender_id) REFERENCES user(user_id),
    FOREIGN KEY (reported_id) REFERENCES user(user_id)
);
alter table reports modify report_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
desc reports;
select * from reports;
drop table reports;
alter table user add column is_deleted int default 0;
SELECT COUNT(*) FROM user WHERE email = ? AND password = ? AND is_deleted = ?;
alter table reports add column content varchar(255);
delete from reports where id=3;
select * from messages;
SELECT * FROM messages WHERE sender_id = 1 AND receiver_id = 3 ORDER BY id DESC LIMIT 1;