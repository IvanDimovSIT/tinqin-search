package com.tinqinacademy.search.restexport;


import com.tinqinacademy.search.api.operations.findcomments.FindCommentsOutput;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentOutput;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers({"Content-Type: application/json"})
public interface SearchRestExport {

    @RequestLine("GET /api/v1/search/comments/{word}")
    FindCommentsOutput findComments(@Param("word") String word);

    @RequestLine("GET /api/v1/search/words/{commentId}")
    FindWordsForCommentOutput findWords(@Param("commentId") String commentId);
}
