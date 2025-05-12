package com.fastcampus.board_server.service;

import java.util.List;

import com.fastcampus.board_server.dto.PostDTO;

public interface PostService {

    void register(String id, PostDTO postDTO);

    List<PostDTO> getMyProducts(int accountId);

    void updateProducts(PostDTO postDTO);

    void deleteProduct(int userId, int productId);
}
