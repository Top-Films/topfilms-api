CREATE TABLE IF NOT EXISTS MOVIE_LIST (
    id UUID PRIMARY KEY,
    created_by UUID NOT NULL,
    updated_by UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    movie_genre_id UUID NOT NULL,
    title VARCHAR(128) NOT NULL,
    description VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS MOVIE_GENRE (
    id UUID PRIMARY KEY,
    created_by UUID NOT NULL,
    updated_by UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    name VARCHAR(128) NOT NULL
);

CREATE TABLE IF NOT EXISTS MOVIE (
    id UUID PRIMARY KEY,
    created_by UUID NOT NULL,
    updated_by UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    name VARCHAR(1024) NOT NULL,
    director VARCHAR(512),
    movie_genre_id UUID NOT NULL,
    year INTEGER
);