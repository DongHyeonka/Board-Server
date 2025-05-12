package com.fastcampus.board_server.service;

import java.util.List;

import com.fastcampus.board_server.dto.PostDTO;
import com.fastcampus.board_server.dto.request.PostSearchRequest;

public interface PostSearchService {
    List<PostDTO> getProducts(PostSearchRequest postSearchRequest);

    List<PostDTO> getPostByTag(String tagName);
}
