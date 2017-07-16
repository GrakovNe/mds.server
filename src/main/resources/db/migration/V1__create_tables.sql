CREATE TABLE action_place_type (
    id integer NOT NULL,
    value character varying(255)
);

CREATE TABLE action_time_type (
    id integer NOT NULL,
    value character varying(255)
);

CREATE TABLE author (
    id integer NOT NULL,
    first_name character varying(255),
    last_name character varying(255)
);

CREATE TABLE cover (
    id integer NOT NULL,
    base64encoded_cover character varying(255),
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

CREATE TABLE plot_type (
    id integer NOT NULL,
    value character varying(255)
);

CREATE TABLE rating (
    id integer NOT NULL,
    value integer,
    voters integer,
    story_id integer
);

CREATE TABLE reader_age_type (
    id integer NOT NULL,
    type character varying(255)
);

CREATE TABLE story (
    id integer NOT NULL,
    annotation character varying(255),
    file_quality bigint,
    file_size bigint,
    length integer,
    title character varying(255),
    url character varying(255),
    year integer,
    cover_id integer,
    rating_id integer
);

CREATE TABLE story_action_place_types (
    story_id integer NOT NULL,
    author_id integer NOT NULL
);

CREATE TABLE story_action_time_type (
    story_id integer NOT NULL,
    author_id integer NOT NULL
);

CREATE TABLE story_authors (
    story_id integer NOT NULL,
    author_id integer NOT NULL
);

CREATE TABLE story_genres (
    story_id integer NOT NULL,
    author_id integer NOT NULL
);

CREATE TABLE story_plot_type (
    author_id integer,
    story_id integer NOT NULL
);

CREATE TABLE story_tags (
    story_id integer NOT NULL,
    author_id integer NOT NULL
);

CREATE TABLE tag (
    id integer NOT NULL,
    value character varying(255)
);

ALTER TABLE ONLY action_place_type
    ADD CONSTRAINT action_place_type_pkey PRIMARY KEY (id);

ALTER TABLE ONLY action_time_type
    ADD CONSTRAINT action_time_type_pkey PRIMARY KEY (id);

ALTER TABLE ONLY author
    ADD CONSTRAINT author_pkey PRIMARY KEY (id);

ALTER TABLE ONLY cover
    ADD CONSTRAINT cover_pkey PRIMARY KEY (id);

ALTER TABLE ONLY genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);

ALTER TABLE ONLY plot_type
    ADD CONSTRAINT plot_type_pkey PRIMARY KEY (id);

ALTER TABLE ONLY rating
    ADD CONSTRAINT rating_pkey PRIMARY KEY (id);

ALTER TABLE ONLY reader_age_type
    ADD CONSTRAINT reader_age_type_pkey PRIMARY KEY (id);

ALTER TABLE ONLY story_action_place_types
    ADD CONSTRAINT story_action_place_types_pkey PRIMARY KEY (story_id, author_id);

ALTER TABLE ONLY story_action_time_type
    ADD CONSTRAINT story_action_time_type_pkey PRIMARY KEY (story_id, author_id);

ALTER TABLE ONLY story_genres
    ADD CONSTRAINT story_genres_pkey PRIMARY KEY (story_id, author_id);

ALTER TABLE ONLY story
    ADD CONSTRAINT story_pkey PRIMARY KEY (id);

ALTER TABLE ONLY story_plot_type
    ADD CONSTRAINT story_plot_type_pkey PRIMARY KEY (story_id);

ALTER TABLE ONLY story_tags
    ADD CONSTRAINT story_tags_pkey PRIMARY KEY (story_id, author_id);

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);

ALTER TABLE ONLY story_plot_type
    ADD CONSTRAINT fk1085growp3n056ixog14rgb83 FOREIGN KEY (author_id) REFERENCES plot_type(id);

ALTER TABLE ONLY story_tags
    ADD CONSTRAINT fk12u2njd3sp1o7nt72c5jnfsla FOREIGN KEY (author_id) REFERENCES tag(id);

ALTER TABLE ONLY story_tags
    ADD CONSTRAINT fk15ld2px3hrrvu2hxmt5fqjs4p FOREIGN KEY (story_id) REFERENCES story(id);

ALTER TABLE ONLY story_action_time_type
    ADD CONSTRAINT fk2skg221ayv2jmkwgd92xrea2l FOREIGN KEY (author_id) REFERENCES action_time_type(id);

ALTER TABLE ONLY cover
    ADD CONSTRAINT fk4cn2bd381xm9cxueq5oijeaiw FOREIGN KEY (story_id) REFERENCES story(id);

ALTER TABLE ONLY story_authors
    ADD CONSTRAINT fk6k9ndu1xetybeobtmief9tc8h FOREIGN KEY (story_id) REFERENCES story(id);

ALTER TABLE ONLY rating
    ADD CONSTRAINT fk80o4s4q6fehrpvw8cca2yxj27 FOREIGN KEY (story_id) REFERENCES story(id);

ALTER TABLE ONLY story_genres
    ADD CONSTRAINT fk88ws4qqewqsepkwgokbeqi862 FOREIGN KEY (author_id) REFERENCES genre(id);

ALTER TABLE ONLY story_action_time_type
    ADD CONSTRAINT fkaax2ru3013so170bbknx02slk FOREIGN KEY (story_id) REFERENCES story(id);

ALTER TABLE ONLY story
    ADD CONSTRAINT fkbh2so206ma10qgxm4sugvybk FOREIGN KEY (cover_id) REFERENCES cover(id);

ALTER TABLE ONLY story
    ADD CONSTRAINT fkeptm8vp3mp46v71h5vl2gysqn FOREIGN KEY (rating_id) REFERENCES rating(id);

ALTER TABLE ONLY story_action_place_types
    ADD CONSTRAINT fketl1jmj0phn9o32y1hulcmry FOREIGN KEY (author_id) REFERENCES action_place_type(id);

ALTER TABLE ONLY story_authors
    ADD CONSTRAINT fkffamqbn9u5u4tsa3usx33iy7d FOREIGN KEY (author_id) REFERENCES author(id);

ALTER TABLE ONLY story_action_place_types
    ADD CONSTRAINT fkmjmh5r7hh3i0t8mv0aqhh29ih FOREIGN KEY (story_id) REFERENCES story(id);

ALTER TABLE ONLY story_genres
    ADD CONSTRAINT fkqn67twps6unol38h40mpaugy FOREIGN KEY (story_id) REFERENCES story(id);

ALTER TABLE ONLY story_plot_type
    ADD CONSTRAINT fksunam3qegbmnwj0ucw1ofiuwg FOREIGN KEY (story_id) REFERENCES story(id);
