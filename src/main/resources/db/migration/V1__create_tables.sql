CREATE TABLE author (
    id integer NOT NULL,
    name character varying(255)
);

CREATE TABLE cover (
    id integer NOT NULL,
    base64encoded_cover character varying(655350),
    story_id integer
);

CREATE TABLE genre (
    id integer NOT NULL,
    value character varying(255)
);

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE rating (
    id integer NOT NULL,
    value double precision,
    voters integer,
    story_id integer
);

CREATE TABLE story (
    id integer NOT NULL,
    annotation character varying(65535),
    file_quality BIGINT,
    file_size BIGINT,
    length integer,
    title character varying(255),
    url character varying(255),
    YEAR integer,
    cover_id integer,
    rating_id integer
);


CREATE TABLE story_authors (
    story_id integer NOT NULL,
    author_id integer NOT NULL
);

CREATE TABLE story_genres (
    story_id integer NOT NULL,
    author_id integer NOT NULL
);


CREATE TABLE story_tags (
    story_id integer NOT NULL,
    author_id integer NOT NULL
);

CREATE TABLE tag (
    id integer NOT NULL,
    value character varying(255)
);

ALTER TABLE ONLY author
    ADD CONSTRAINT author_pkey PRIMARY KEY (id);

ALTER TABLE ONLY cover
    ADD CONSTRAINT cover_pkey PRIMARY KEY (id);

ALTER TABLE ONLY genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);

ALTER TABLE ONLY rating
    ADD CONSTRAINT rating_pkey PRIMARY KEY (id);

ALTER TABLE ONLY story_genres
    ADD CONSTRAINT story_genres_pkey PRIMARY KEY (story_id, author_id);

ALTER TABLE ONLY story
    ADD CONSTRAINT story_pkey PRIMARY KEY (id);

ALTER TABLE ONLY story_tags
    ADD CONSTRAINT story_tags_pkey PRIMARY KEY (story_id, author_id);

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);

ALTER TABLE ONLY story_tags
    ADD CONSTRAINT fk12u2njd3sp1o7nt72c5jnfsla FOREIGN KEY (author_id) REFERENCES tag(id);

ALTER TABLE ONLY story_tags
    ADD CONSTRAINT fk15ld2px3hrrvu2hxmt5fqjs4p FOREIGN KEY (story_id) REFERENCES story(id);

ALTER TABLE ONLY cover
    ADD CONSTRAINT fk4cn2bd381xm9cxueq5oijeaiw FOREIGN KEY (story_id) REFERENCES story(id);

ALTER TABLE ONLY story_authors
    ADD CONSTRAINT fk6k9ndu1xetybeobtmief9tc8h FOREIGN KEY (story_id) REFERENCES story(id);

ALTER TABLE ONLY rating
    ADD CONSTRAINT fk80o4s4q6fehrpvw8cca2yxj27 FOREIGN KEY (story_id) REFERENCES story(id);

ALTER TABLE ONLY story_genres
    ADD CONSTRAINT fk88ws4qqewqsepkwgokbeqi862 FOREIGN KEY (author_id) REFERENCES genre(id);

ALTER TABLE ONLY story
    ADD CONSTRAINT fkbh2so206ma10qgxm4sugvybk FOREIGN KEY (cover_id) REFERENCES cover(id);

ALTER TABLE ONLY story
    ADD CONSTRAINT fkeptm8vp3mp46v71h5vl2gysqn FOREIGN KEY (rating_id) REFERENCES rating(id);

ALTER TABLE ONLY story_authors
    ADD CONSTRAINT fkffamqbn9u5u4tsa3usx33iy7d FOREIGN KEY (author_id) REFERENCES author(id);

ALTER TABLE ONLY story_genres
    ADD CONSTRAINT fkqn67twps6unol38h40mpaugy FOREIGN KEY (story_id) REFERENCES story(id);
