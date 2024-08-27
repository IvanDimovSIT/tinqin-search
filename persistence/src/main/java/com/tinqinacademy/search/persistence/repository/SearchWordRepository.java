package com.tinqinacademy.search.persistence.repository;

import com.tinqinacademy.search.persistence.model.SearchWord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SearchWordRepository extends MongoRepository<SearchWord, UUID> {
    List<SearchWord> findByWord(String word);
    List<SearchWord> findByCommentId(UUID commentId);
    void deleteAllByWordAndCommentId(String word, UUID commentId);
}
