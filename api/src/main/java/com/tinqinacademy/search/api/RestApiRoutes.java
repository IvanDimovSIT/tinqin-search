package com.tinqinacademy.search.api;

public class RestApiRoutes {
    public static final String API = "/api/v1";

    public static final String SEARCH_BASE = API + "/search";

    public static final String SEARCH_ADD_SEARCH_WORDS = SEARCH_BASE + "/words";
    public static final String SEARCH_FIND_COMMENTS_WORDS = SEARCH_BASE + "/comments/{word}";
}
