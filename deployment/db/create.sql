create table blog_posts (author_id bigint not null, id bigserial not null, publication_date timestamp(6), content TEXT, title varchar(255), primary key (id));
create table users (id bigserial not null, email varchar(255) unique, firstname varchar(255), lastname varchar(255), password varchar(255), phone varchar(255), username varchar(255) unique, primary key (id));
alter table if exists blog_posts add constraint FKlog64k5g2l1679hjl2wuyyk5n foreign key (author_id) references users;
