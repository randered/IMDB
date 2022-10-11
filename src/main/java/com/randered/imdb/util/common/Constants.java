package com.randered.imdb.util.common;

public final class Constants {

    private Constants() {
    }

    public static final String BASE_PATH = "/api";
    public static final String PATH_USER = "/user";
    public static final String PATH_ADMIN = "/admin";
    public static final String RATE_PATH = "/rate";
    public static final String LOGIN = BASE_PATH + "/login";

    public static final String ALLOW_ALL = "/**";

    public static final String MOVIES = "/movies";
    public static final String CREATE_MOVIE = "/movie/create";
    public static final String DELETE_MOVIE = "/movie/delete";

    public static final String IMAGE_MOVIE = "/movie/image";

    public static final String ADMIN_DELETE_MOVIE = PATH_ADMIN + DELETE_MOVIE;


    public static final String LIST_MOVIES = BASE_PATH + MOVIES;

    public static final String ADMIN_CREATE_MOVIE = BASE_PATH + PATH_ADMIN + CREATE_MOVIE;
    public static final String ROLE = "role";
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    public static final String REGISTER = "/register";

    public static final String REFRESH_URL = "/refresh";
    public static final String SUCCESSFUL_REGISTER = "User registered.";

    public static final String SUCCESSFUL_IMAGE_UPLOAD = "User registered.";
    public static final String SUCCESSFUL_RATING = "Rating posted.";
    public static final String MOVIE_CREATED = "Movie created.";
    public static final String MOVIE_DELETED = "Movie deleted.";

    public static final String USER_ALREADY_EXISTS = "User with username %s, already exists.";
    public static final String USER_NOT_FOUND_MSG = "User: %s not found.";
    public static final String USER_ID_NOT_FOUND_MSG = "User ID not found.";

    // Security
    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 900_000; // 15 mins

    public static final long REFRESH_EXPIRATION_TIME = 900_000_000; // 10 days approx

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_REFRESH = "/token/refresh";
    public static final String AUTHORITY = "authority";

    public static final String SECURITY_CONFIG_PREFIX = "security";
    public static final String ANT_MATCHERS = "\"/configuration/ui\",\n" +
                                              "\"/configuration/**\",\n" +
                                              "\"/actuator/**\",\n" +
                                              "\"/v3/api-docs/**\",\n" +
                                              "\"/swagger-resources/**\",\n" +
                                              "\"/swagger-ui/**\",\n" +
                                              "\"/swagger-ui.html\",\n" +
                                              "\"/webjars/**\" ";
}