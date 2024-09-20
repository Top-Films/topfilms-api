CREATE TABLE IF NOT EXISTS MOVIE_LIST (
    id UUID PRIMARY KEY,
    created_by UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    movie_genre_id UUID,
    title VARCHAR(64) NOT NULL,
    likes INTEGER,
    views INTEGER
);

CREATE TABLE IF NOT EXISTS MOVIE_GENRE (
    id UUID PRIMARY KEY,
    created_by UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS MOVIE (
    id UUID PRIMARY KEY,
    created_by UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    name VARCHAR(128) NOT NULL,
    director VARCHAR(128) NOT NULL,
    genre VARCHAR(64) NOT NULL,
    year INTEGER NOT NULL
);