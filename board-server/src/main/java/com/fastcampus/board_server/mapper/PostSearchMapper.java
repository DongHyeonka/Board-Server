package com.fastcampus.board_server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fastcampus.board_server.dto.PostDTO;
import com.fastcampus.board_server.dto.request.PostSearchRequest;

@Mapper
public interface PostSearchMapper {
    public List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);
    
    public List<PostDTO> getPostByTag(String tagName);
}
