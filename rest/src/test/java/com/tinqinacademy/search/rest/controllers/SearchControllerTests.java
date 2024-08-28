package com.tinqinacademy.search.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.search.api.RestApiRoutes;
import com.tinqinacademy.search.api.operations.findcomments.FindCommentsOutput;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentOutput;
import com.tinqinacademy.search.persistence.model.SearchWord;
import com.tinqinacademy.search.persistence.repository.SearchWordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SearchControllerTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SearchWordRepository searchWordRepository;

    @BeforeEach
    public void setUp() throws Exception {
        UUID commentId = UUID.randomUUID();

        searchWordRepository.save(SearchWord.builder()
                        .id(UUID.randomUUID())
                        .commentId(commentId)
                        .word("ABC")
                .build());

        searchWordRepository.save(SearchWord.builder()
                .id(UUID.randomUUID())
                .commentId(commentId)
                .word("DEF")
                .build());
    }

    @AfterEach
    public void tearDown() throws Exception {
        searchWordRepository.deleteAll();
    }

    @Test
    public void testFindCommentsOk() throws Exception {
        String word = "ABC";

        String result = mvc.perform(get(RestApiRoutes.SEARCH_FIND_COMMENTS_WORDS, word))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        FindCommentsOutput findCommentsOutput = objectMapper.readValue(result, FindCommentsOutput.class);
        assertEquals(findCommentsOutput.getCommentIds().size(), 1);
    }

    @Test
    public void testFindCommentsNotFound() throws Exception {
        mvc.perform(get(RestApiRoutes.SEARCH_FIND_COMMENTS_WORDS, ""))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindCommentsBadRequest() throws Exception {
        String word = " ";

        mvc.perform(get(RestApiRoutes.SEARCH_FIND_COMMENTS_WORDS, word))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindWordsOk() throws Exception {
        UUID commentId = searchWordRepository.findAll().getFirst().getCommentId();

        String result = mvc.perform(get(RestApiRoutes.SEARCH_FIND_WORDS, commentId.toString()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        FindWordsForCommentOutput forCommentOutput = objectMapper.readValue(result, FindWordsForCommentOutput.class);

        assertEquals(forCommentOutput.getWords().size(), 2);
    }

    @Test
    public void testFindWordsNotFound() throws Exception {
        mvc.perform(get(RestApiRoutes.SEARCH_FIND_WORDS, ""))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindWordsBadRequest() throws Exception {
        String commentId = " ";

        mvc.perform(get(RestApiRoutes.SEARCH_FIND_WORDS, commentId))
                .andExpect(status().isBadRequest());
    }


}
