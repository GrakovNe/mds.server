CREATE TABLE author (
  id   SERIAL NOT NULL,
  name CHARACTER VARYING(255)
);

CREATE TABLE cover (
  id                  SERIAL NOT NULL,
  base64encoded_cover CHARACTER VARYING(655350),
  story_id            INTEGER
);

CREATE TABLE rating (
  id       SERIAL NOT NULL,
  value    DOUBLE PRECISION,
  voters   INTEGER,
  story_id INTEGER
);

CREATE TABLE story (
  id           SERIAL NOT NULL,
  annotation   CHARACTER VARYING(65535),
  file_quality BIGINT,
  file_size    BIGINT,
  length       INTEGER,
  title        CHARACTER VARYING(255),
  url          CHARACTER VARYING(255),
  YEAR         INTEGER,
  cover_id     INTEGER,
  rating_id    INTEGER
);


CREATE TABLE story_authors (
  story_id  INTEGER NOT NULL,
  author_id INTEGER NOT NULL
);


CREATE TABLE story_tags (
  story_id  INTEGER NOT NULL,
  author_id INTEGER NOT NULL
);

CREATE TABLE tag (
  id    SERIAL NOT NULL,
  value CHARACTER VARYING(255)
);

CREATE TABLE users
(
  id                         SERIAL NOT NULL,
  is_account_non_expired     BOOLEAN,
  is_account_non_locked      BOOLEAN,
  is_credentials_non_expired BOOLEAN,
  is_enabled                 BOOLEAN,
  password                   CHARACTER VARYING(255),
  username                   CHARACTER VARYING(255)
);

ALTER TABLE ONLY users
ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY author
ADD CONSTRAINT author_pkey PRIMARY KEY (id);

ALTER TABLE ONLY cover
ADD CONSTRAINT cover_pkey PRIMARY KEY (id);

ALTER TABLE ONLY rating
ADD CONSTRAINT rating_pkey PRIMARY KEY (id);

ALTER TABLE ONLY story
ADD CONSTRAINT story_pkey PRIMARY KEY (id);

ALTER TABLE ONLY story_tags
ADD CONSTRAINT story_tags_pkey PRIMARY KEY (story_id, author_id);

ALTER TABLE ONLY tag
ADD CONSTRAINT tag_pkey PRIMARY KEY (id);

ALTER TABLE ONLY story_tags
ADD CONSTRAINT fk12u2njd3sp1o7nt72c5jnfsla FOREIGN KEY (author_id) REFERENCES tag(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ONLY story_tags
ADD CONSTRAINT fk15ld2px3hrrvu2hxmt5fqjs4p FOREIGN KEY (story_id) REFERENCES story(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ONLY cover
ADD CONSTRAINT fk4cn2bd381xm9cxueq5oijeaiw FOREIGN KEY (story_id) REFERENCES story(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ONLY story_authors
ADD CONSTRAINT fk6k9ndu1xetybeobtmief9tc8h FOREIGN KEY (story_id) REFERENCES story(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ONLY rating
ADD CONSTRAINT fk80o4s4q6fehrpvw8cca2yxj27 FOREIGN KEY (story_id) REFERENCES story(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ONLY story
ADD CONSTRAINT fkbh2so206ma10qgxm4sugvybk FOREIGN KEY (cover_id) REFERENCES cover(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ONLY story
ADD CONSTRAINT fkeptm8vp3mp46v71h5vl2gysqn FOREIGN KEY (rating_id) REFERENCES rating(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE ONLY story_authors
ADD CONSTRAINT fkffamqbn9u5u4tsa3usx33iy7d FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE story_bookmark
(
  id               SERIAL NOT NULL,
  create_date_time BYTEA,
  "timestamp"      INTEGER,
  story_id         INTEGER,
  user_id          INTEGER,
  CONSTRAINT story_bookmark_pkey PRIMARY KEY (id),
  CONSTRAINT fk654v0rnjkqrumlywb1w6puych FOREIGN KEY (user_id)
  REFERENCES users (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fklln11dxblqln44dgevrdkhvn2 FOREIGN KEY (story_id)
REFERENCES story (id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE listened_story
(
  id               SERIAL NOT NULL,
  create_date_time BYTEA,
  story_id         INTEGER,
  user_id          INTEGER,
  CONSTRAINT listened_story_pkey PRIMARY KEY (id),
  CONSTRAINT fkfljr34vtdnf24rm070it36al1 FOREIGN KEY (story_id)
  REFERENCES story (id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fkrp7ecagsnibg1web5fn32pdme FOREIGN KEY (user_id)
REFERENCES users (id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE
);