package com.fastcampus.board_server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fastcampus.board_server.dto.PostDTO;

@Mapper
public interface PostMapper {
    public int register(PostDTO postDTO);

    public List<PostDTO> selectMyProducts(int accountId);

    public void updateProducts(PostDTO postDTO);

    public void deleteProduct(int productId);
}