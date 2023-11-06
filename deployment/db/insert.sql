-- Insert Users --
INSERT INTO users
(id, email, firstname, lastname, "password", phone, username)
VALUES(nextval('users_id_seq'::regclass), 'PatrickCColeman@rhyta.com', 'Patrick', 'Coleman', '$2a$12$d31QwoXBFCs3r70yRp.fO.G/WfeqSY.xNPhNt1LHpDWHWobWJlffO', '925-260-1527', 'patrick');

INSERT INTO users
(id, email, firstname, lastname, "password", phone, username)
VALUES(nextval('users_id_seq'::regclass), 'JosephJGraham@dayrep.com', 'Joseph', 'Graham', '$2a$12$ANLTd3b1nFq3okg9EeaGrO.sT1jIYRkVi4VPZxMXg6dkxfyQQe6ei', '
701-348-7442', 'joseph');

INSERT INTO users
(id, email, firstname, lastname, "password", phone, username)
VALUES(nextval('users_id_seq'::regclass), 'CharlesKChamberlain@armyspy.com', 'Charles', 'Chamberlain', '$2a$12$qTmfdSV.btAnRM4/KhMNzeDPbKHOsTfHIsKn2Arf4GNqJEEkEPqhe', '
0681 99 41 07', 'charles');

-- Insert Blog Posts --

INSERT INTO blog_posts
(author_id, id, publication_date, "content", title)
VALUES(1, nextval('blog_posts_id_seq'::regclass), '2023-11-05 20:36:03.133', 'Aut ipsam magnam hic quia nesciunt sit deserunt placeat ex dolorum adipisci. Est sint odio sit distinctio voluptatem 33 delectus labore. Eos possimus corporis qui omnis mollitia est magni officia hic quia mollitia. In perspiciatis saepe ut ratione reiciendis et pariatur veritatis aut nisi commodi et quis sapiente At iure dolores sed iste iusto?', 'Aut ipsam magnam hic quia');

INSERT INTO blog_posts
(author_id, id, publication_date, "content", title)
VALUES(1, nextval('blog_posts_id_seq'::regclass), '2023-11-05 16:36:03.133', 'Ab temporibus nobis eum ratione officiis 33 pariatur maiores et repudiandae nihil ut impedit fugiat et vitae nemo est maxime omnis. Sed placeat libero ut blanditiis laudantium qui voluptas nisi quo itaque autem.\n Qui magnam voluptatem et Quis necessitatibus sed ipsum vitae vel mollitia natus qui officiis nobis sed nobis quam. Non dolores aliquam ea impedit internos est placeat accusantium aut velit dolores.', 'Ab temporibus nobis eum ratione officiis 33');

INSERT INTO blog_posts
(author_id, id, publication_date, "content", title)
VALUES(2, nextval('blog_posts_id_seq'::regclass), '2023-11-05 16:50:03.133', 'Non magni ipsam aut dolorem autem ex libero beatae sed minima impedit. Vel quis necessitatibus qui odit quia qui nihil iure et harum doloremque sed quidem rerum est molestias itaque est dolorum fuga. Et iusto amet id modi cupiditate et reprehenderit quidem qui numquam minus est aperiam repudiandae qui labore harum.', 'Non magni ipsam aut dolorem autem ex libero');

INSERT INTO blog_posts
(author_id, id, publication_date, "content", title)
VALUES(3, nextval('blog_posts_id_seq'::regclass), '2023-11-05 21:12:03.133', 'Sed dolores quam qui aliquam omnis hic magni dolores? At tempore alias ut asperiores veritatis sed reiciendis temporibus eum repudiandae corrupti id asperiores laborum. Ab itaque saepe in earum commodi est quasi nemo est omnis quia.', 'Sed dolores quam qui aliquam');

INSERT INTO blog_posts
(author_id, id, publication_date, "content", title)
VALUES(3, nextval('blog_posts_id_seq'::regclass), '2023-11-05 21:12:03.133', 'Eum deleniti accusantium est quas dolores ut rerum dolorem ad ipsum nihil ut dolore voluptate et tenetur porro. Qui quidem voluptates quo voluptas quia ad harum culpa ut quia necessitatibus ad culpa quia ab magnam itaque.\n Eum perspiciatis quis ut iure voluptas vel recusandae labore ea deleniti ipsum est animi illum? Non reiciendis cumque non adipisci laudantium et enim rerum.', 'Eum deleniti accusantium est quas dolores');