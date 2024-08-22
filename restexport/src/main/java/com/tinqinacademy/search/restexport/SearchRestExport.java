package com.tinqinacademy.search.restexport;


import com.tinqinacademy.search.api.RestApiRoutes;
import com.tinqinacademy.search.api.operations.findcomments.FindCommentsOutput;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.vavr.API;

@Headers({"Content-Type: application/json"})
public interface SearchRestExport {

    @RequestLine("GET /api/v1/search/comments/{word}")
    FindCommentsOutput findComments(@Param("word") String word);
}
