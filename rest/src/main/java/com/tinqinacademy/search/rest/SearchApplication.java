package com.tinqinacademy.search.rest;


import com.tinqinacademy.search.persistence.model.SearchWord;
import com.tinqinacademy.search.persistence.repository.SearchWordRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.UUID;


@SpringBootApplication
@ComponentScan(basePackages = "com.tinqinacademy.search")
@EnableMongoRepositories(basePackages = "com.tinqinacademy.search.persistence.repository")
public class SearchApplication {
    //@Autowired
    //private SearchWordRepository repository;

    //@PostConstruct
    //public void testDb(){
    //    SearchWord searchWord = SearchWord.builder()
    //            .id(UUID.randomUUID())
    //            .name("example1")
    //            .build();
    //    SearchWord test = repository.save(searchWord);
    //    System.out.println("Created entity:" + test);
    //    SearchWord found = repository.findById(test.getId())
    //            .orElseThrow(() -> new RuntimeException("Test entity not found"));
    //    System.out.println("Found entity:" + found);
    //}

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

}
