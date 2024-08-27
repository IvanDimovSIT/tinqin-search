package com.tinqinacademy.search.rest.controllers;

import com.tinqinacademy.search.api.RestApiRoutes;
import com.tinqinacademy.search.api.errors.Errors;
import com.tinqinacademy.search.api.operations.addsearchword.AddSearchWordInput;
import com.tinqinacademy.search.api.operations.addsearchword.AddSearchWordOperation;
import com.tinqinacademy.search.api.operations.addsearchword.AddSearchWordOutput;
import com.tinqinacademy.search.api.operations.deleteword.DeleteWordInput;
import com.tinqinacademy.search.api.operations.deleteword.DeleteWordOperation;
import com.tinqinacademy.search.api.operations.deleteword.DeleteWordOutput;
import com.tinqinacademy.search.api.operations.findcomments.FindCommentsInput;
import com.tinqinacademy.search.api.operations.findcomments.FindCommentsOperation;
import com.tinqinacademy.search.api.operations.findcomments.FindCommentsOutput;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentInput;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentOperation;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class SearchController extends BaseController{
    private final AddSearchWordOperation addSearchWordOperation;
    private final FindCommentsOperation findCommentsOperation;
    private final FindWordsForCommentOperation findWordsForCommentOperation;
    private final DeleteWordOperation deleteWordOperation;

    // For testing
    @Operation(summary = "(TEST)Add search words", description = "Adds search words for a comment. Used for testing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Words added"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @PostMapping(RestApiRoutes.SEARCH_ADD_SEARCH_WORDS)
    public ResponseEntity<?> addSearchWords(@RequestBody AddSearchWordInput input) {
        Either<Errors, AddSearchWordOutput> output = addSearchWordOperation.process(input);
        return mapToResponseEntity(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Find comments for word", description = "Finds comments for search word")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @GetMapping(RestApiRoutes.SEARCH_FIND_COMMENTS_WORDS)
    public ResponseEntity<?> findComments(@PathVariable String word) {
        FindCommentsInput input = FindCommentsInput.builder()
                .word(word)
                .build();

        Either<Errors, FindCommentsOutput> output = findCommentsOperation.process(input);
        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Delete words", description = "Deletes words by commentId and word string")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Words delete"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @DeleteMapping(RestApiRoutes.SEARCH_DELETE_WORDS)
    public ResponseEntity<?> deleteWords(@RequestParam String commentId, @RequestParam String word) {
        DeleteWordInput input = DeleteWordInput.builder()
                .word(word)
                .commentId(commentId)
                .build();

        Either<Errors, DeleteWordOutput> output = deleteWordOperation.process(input);
        return mapToResponseEntity(output, HttpStatus.OK);
    }

    @Operation(summary = "Find words for commentId", description = "Finds all the words in a comment by commentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Words found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })

    @GetMapping(RestApiRoutes.SEARCH_FIND_WORDS)
    public ResponseEntity<?> findWords(@PathVariable String commentId) {
        FindWordsForCommentInput input = FindWordsForCommentInput.builder()
                .commentId(commentId)
                .build();

        Either<Errors, FindWordsForCommentOutput> output = findWordsForCommentOperation.process(input);
        return mapToResponseEntity(output, HttpStatus.OK);
    }
}
