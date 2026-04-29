/* Drop in dependency order (children first) */
DROP TABLE IF EXISTS review_environment_tags;
DROP TABLE IF EXISTS review_service_tags;
DROP TABLE IF EXISTS review_food_tags;
DROP TABLE IF EXISTS post_service_tags;
DROP TABLE IF EXISTS post_food_tags;
DROP TABLE IF EXISTS post_environment_tags;
DROP TABLE IF EXISTS establishment_environment_tags;
DROP TABLE IF EXISTS establishment_service_tags;
DROP TABLE IF EXISTS establishment_food_tags;
DROP TABLE IF EXISTS client_disliked_food_tags;
DROP TABLE IF EXISTS client_liked_food_tags;
DROP TABLE IF EXISTS comment_likes;
DROP TABLE IF EXISTS saved_reviews;
DROP TABLE IF EXISTS saved_posts;
DROP TABLE IF EXISTS post_likes;
DROP TABLE IF EXISTS blocked_users;
DROP TABLE IF EXISTS follows;
DROP TABLE IF EXISTS user_activities;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS establishments;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS service_tags;
DROP TABLE IF EXISTS food_tags;
DROP TABLE IF EXISTS environment_tags;

CREATE TABLE users
(
    id                            TEXT PRIMARY KEY,
    user_type                     TEXT     NOT NULL CHECK (user_type IN ('client', 'establishment')),
    email                         TEXT UNIQUE NOT NULL,
    phone_number                  TEXT,
    username                      TEXT UNIQUE NOT NULL,
    password                      TEXT     NOT NULL,
    profile_picture_url           TEXT,
    account_creation_date         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_account_confirmed          BOOLEAN  NOT NULL DEFAULT 0,
    confirmation_code             TEXT,
    confirmation_code_expires_at  DATETIME,
    confirmation_code_created_at  DATETIME,
    bio                           TEXT
);

CREATE TABLE clients
(
    user_id TEXT PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE establishments
(
    user_id               TEXT PRIMARY KEY,
    cnpj                  TEXT UNIQUE NOT NULL,
    name                  TEXT        NOT NULL,
    establishment_type    TEXT        NOT NULL,
    is_delivery           BOOLEAN     NOT NULL DEFAULT 0,
    is_presencial         BOOLEAN     NOT NULL DEFAULT 0,
    is_visible            BOOLEAN     DEFAULT 0,
    status                TEXT        NOT NULL DEFAULT 'PENDING',
    rejections            INTEGER     NOT NULL DEFAULT 0 CHECK (rejections >= 0 AND rejections <= 2),
    rejection_date        DATETIME,
    address_cep           TEXT,
    address_city          TEXT,
    address_state         TEXT,
    address_neighbourhood TEXT,
    address_street        TEXT,
    address_number        TEXT,
    address_complement    TEXT,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE reviews
(
    id                        TEXT PRIMARY KEY,
    user_id                   TEXT     NOT NULL,
    description               TEXT,
    created_at                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reviewed_establishment_id TEXT,
    price_rate                REAL,
    food_rate                 REAL,
    establishment_rate        REAL,
    service_rate              REAL,
    delivery_rate             REAL,
    is_delivery_review        BOOLEAN  NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (reviewed_establishment_id) REFERENCES establishments (user_id) ON DELETE SET NULL
);

CREATE TABLE review_food_tags
(
    review_id TEXT NOT NULL,
    food_tag  TEXT NOT NULL,
    PRIMARY KEY (review_id, food_tag),
    FOREIGN KEY (review_id) REFERENCES reviews (id) ON DELETE CASCADE
);

CREATE TABLE review_service_tags
(
    review_id  TEXT NOT NULL,
    service_tag TEXT NOT NULL,
    PRIMARY KEY (review_id, service_tag),
    FOREIGN KEY (review_id) REFERENCES reviews (id) ON DELETE CASCADE
);

CREATE TABLE review_environment_tags
(
    review_id         TEXT NOT NULL,
    environment_tag  TEXT NOT NULL,
    PRIMARY KEY (review_id, environment_tag),
    FOREIGN KEY (review_id) REFERENCES reviews (id) ON DELETE CASCADE
);

CREATE TABLE comments
(
    id                TEXT PRIMARY KEY,
    review_id         TEXT     NOT NULL,
    user_id           TEXT     NOT NULL,
    parent_comment_id TEXT,
    description       TEXT     NOT NULL,
    created_at        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (review_id) REFERENCES reviews (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (parent_comment_id) REFERENCES comments (id) ON DELETE CASCADE
);

CREATE TABLE images
(
    id               TEXT PRIMARY KEY,
    establishment_id TEXT,
    review_id        TEXT,
    url              TEXT     NOT NULL,
    image_type       TEXT     NOT NULL,
    created_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK ((establishment_id IS NOT NULL AND review_id IS NULL) OR (establishment_id IS NULL AND review_id IS NOT NULL)),
    FOREIGN KEY (establishment_id) REFERENCES establishments (user_id) ON DELETE CASCADE,
    FOREIGN KEY (review_id) REFERENCES reviews (id) ON DELETE CASCADE
);

CREATE TABLE follows
(
    follower_id  TEXT NOT NULL,
    following_id TEXT NOT NULL,
    PRIMARY KEY (follower_id, following_id),
    FOREIGN KEY (follower_id) REFERENCES clients (user_id) ON DELETE CASCADE,
    FOREIGN KEY (following_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE saved_reviews
(
    user_id   TEXT NOT NULL,
    review_id TEXT NOT NULL,
    PRIMARY KEY (user_id, review_id),
    FOREIGN KEY (user_id) REFERENCES clients (user_id) ON DELETE CASCADE,
    FOREIGN KEY (review_id) REFERENCES reviews (id) ON DELETE CASCADE
);

CREATE TABLE comment_likes
(
    user_id    TEXT NOT NULL,
    comment_id TEXT NOT NULL,
    PRIMARY KEY (user_id, comment_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (comment_id) REFERENCES comments (id) ON DELETE CASCADE
);

CREATE TABLE client_liked_food_tags
(
    client_id TEXT NOT NULL,
    food_tag  TEXT NOT NULL,
    PRIMARY KEY (client_id, food_tag),
    FOREIGN KEY (client_id) REFERENCES clients (user_id) ON DELETE CASCADE
);

CREATE TABLE client_disliked_food_tags
(
    client_id TEXT NOT NULL,
    food_tag  TEXT NOT NULL,
    PRIMARY KEY (client_id, food_tag),
    FOREIGN KEY (client_id) REFERENCES clients (user_id) ON DELETE CASCADE
);

CREATE TABLE establishment_food_tags
(
    establishment_id TEXT NOT NULL,
    food_tag         TEXT NOT NULL,
    PRIMARY KEY (establishment_id, food_tag),
    FOREIGN KEY (establishment_id) REFERENCES establishments (user_id) ON DELETE CASCADE
);

CREATE TABLE establishment_service_tags
(
    establishment_id TEXT NOT NULL,
    service_tag     TEXT NOT NULL,
    PRIMARY KEY (establishment_id, service_tag),
    FOREIGN KEY (establishment_id) REFERENCES establishments (user_id) ON DELETE CASCADE
);

CREATE TABLE establishment_environment_tags
(
    establishment_id  TEXT NOT NULL,
    environment_tag  TEXT NOT NULL,
    PRIMARY KEY (establishment_id, environment_tag),
    FOREIGN KEY (establishment_id) REFERENCES establishments (user_id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_reviews_user_id ON reviews (user_id);
CREATE INDEX IF NOT EXISTS idx_reviews_reviewed_establishment_id ON reviews (reviewed_establishment_id);
CREATE INDEX IF NOT EXISTS idx_comments_review_id ON comments (review_id);
CREATE INDEX IF NOT EXISTS idx_comments_user_id ON comments (user_id);
CREATE INDEX IF NOT EXISTS idx_comments_parent_comment_id ON comments (parent_comment_id);
CREATE INDEX IF NOT EXISTS idx_images_establishment_id ON images (establishment_id);
CREATE INDEX IF NOT EXISTS idx_images_review_id ON images (review_id);
CREATE INDEX IF NOT EXISTS idx_follows_following_id ON follows (following_id);
CREATE INDEX IF NOT EXISTS idx_comment_likes_comment_id ON comment_likes (comment_id);
