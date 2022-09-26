CREATE TABLE hibernate_sequence
(
    next_val bigint NOT NULL
);

INSERT INTO hibernate_sequence (next_val)
VALUES (0);

-- Create Movies Table
CREATE TABLE movies
(
    id      int NOT NULL,
    name    VARCHAR(255),
    year    int,
    genre   VARCHAR(255),
    image   VARCHAR(255),
    trailer VARCHAR(255),
    PRIMARY KEY (`id`)
);

-- Create Actors Table
CREATE TABLE actors
(
    id   int NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (`id`)
);

CREATE TABLE movies_actors
(
    `movies_id` int,
    `actors_id` int,
    KEY         `FKhfh9dx7w3ubf1co1vdev94g32` (`movies_id`),
    KEY         `FKhfh9dx7w3ubf1co1vdev94g33` (`actors_id`),
    CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g32` FOREIGN KEY (`movies_id`) REFERENCES `movies` (`id`),
    CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g33` FOREIGN KEY (`actors_id`) REFERENCES `actors` (`id`)
);

-- Create Users Table
CREATE TABLE users
(
    id        int NOT NULL,
    username  VARCHAR(255),
    password  VARCHAR(255),
    full_name VARCHAR(255),
    authority VARCHAR(255),
    rating_id int,
    PRIMARY KEY (`id`)
);

-- Create Ratings Table
CREATE TABLE ratings
(
    id       int    NOT NULL,
    user_id  int,
    movie_id int,
    rating   DOUBLE NOT NULL,
    comment  VARCHAR(255),
    PRIMARY KEY (`id`),
    KEY      `fk_ratings_users` (`user_id`),
    KEY      `fk_ratings_movies` (`movie_id`),
    CONSTRAINT `fk_ratings_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_ratings_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
);