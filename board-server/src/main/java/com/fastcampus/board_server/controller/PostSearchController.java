package com.fastcampus.board_server.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.board_server.dto.PostDTO;
import com.fastcampus.board_server.dto.request.PostSearchRequest;
import com.fastcampus.board_server.service.PostSearchService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchService postSearchService;

    @PostMapping
    public PostSearchResponse search(@RequestBody PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = postSearchService.getProducts(postSearchRequest);
        return new PostSearchResponse(postDTOList);
    }
    
    @GetMapping
    public PostSearchResponse searchByTagName(String tagName) {
        List<PostDTO> postDTOList = postSearchService.getPostByTag(tagName);
        return new PostSearchResponse(postDTOList);
    }

    // -------------- response 객체 --------------

    @Getter
    @AllArgsConstructor
    private static class PostSearchResponse {
        private List<PostDTO> postDTOList;
    }
}
