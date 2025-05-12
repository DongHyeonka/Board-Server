package com.fastcampus.board_server.service.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fastcampus.board_server.dto.PostDTO;
import com.fastcampus.board_server.dto.request.PostSearchRequest;
import com.fastcampus.board_server.mapper.PostSearchMapper;
import com.fastcampus.board_server.service.PostSearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class PostSearchServiceImpl implements PostSearchService {

    private final PostSearchMapper productSearchMapper;

    @Async
    @Cacheable(value = "getProducts", key = "'getProducts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getProducts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = productSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            log.error("selectPosts 실패");
        }
        return postDTOList;
    }
}
