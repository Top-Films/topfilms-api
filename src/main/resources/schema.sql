CREATE TABLE IF NOT EXISTS MOVIE_LIST_DETAIL (
    id UUID PRIMARY KEY,
    created_by UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    topic_id UUID,
    title VARCHAR(64) NOT NULL,
    likes INTEGER,
    views INTEGER
);

CREATE TABLE IF NOT EXISTS MOVIE_LIST_GENRE (
    id UUID PRIMARY KEY,
    created_by UUID NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    name VARCHAR(64) NOT NULL
);
