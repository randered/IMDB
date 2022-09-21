CREATE TABLE `hibernate_sequence`
(
    next_val bigint DEFAULT NULL
);
-- Create Movies Table
CREATE TABLE movies
(
    id         int NOT NULL,
    name       VARCHAR(255),
    year       int NOT NULL,
    ratings_id int NOT NULL,
    actors_id  int NOT NULL,
    genre      VARCHAR(255),
    image      VARCHAR(255),
    trailer    VARCHAR(255),
    PRIMARY KEY (`id`)
);

-- Create Roles Table
CREATE TABLE roles
(
    id   int          NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Create Actors Table
CREATE TABLE actors
(
    id        int NOT NULL,
    name      VARCHAR(255),
    movies_id int NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE movies_actors
(
    `movies_id` int NOT NULL,
    `actors_id` int DEFAULT NULL,
    KEY        `FKhfh9dx7w3ubf1co1vdev94g32` (`movies_id`),
    CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g32` FOREIGN KEY (`movies_id`) REFERENCES `movies` (`id`)
);

-- Create Users Table
CREATE TABLE users
(
    id       int NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    full_name VARCHAR(255),
    PRIMARY KEY (`id`)
);

-- Create Ratings Table
CREATE TABLE ratings
(
    id       int NOT NULL,
    user_id  int NOT NULL,
    movie_id int NOT NULL,
    rating   int NOT NULL,
    comment  VARCHAR(255),
    PRIMARY KEY (`id`),
    KEY      `fk_ratings_users` (`user_id`),
    KEY      `fk_ratings_movies` (`movie_id`),
    CONSTRAINT `fk_ratings_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_ratings_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
);

-- Create User-Roles Table
CREATE TABLE user_roles
(
    `user_id` int NOT NULL,
    `role_id` int DEFAULT NULL,
    KEY       `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`),
    CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

-- Change PK FK keys for User-Roles table
ALTER TABLE user_roles
    ADD CONSTRAINT PK_user_roles PRIMARY KEY (user_id, role_id),
    ADD CONSTRAINT FK_user_roles_users FOREIGN KEY (role_id) REFERENCES roles (id);