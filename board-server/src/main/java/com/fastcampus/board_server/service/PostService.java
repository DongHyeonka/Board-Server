package com.fastcampus.board_server.service;

import java.util.List;

import com.fastcampus.board_server.dto.CommentDTO;
import com.fastcampus.board_server.dto.PostDTO;
import com.fastcampus.board_server.dto.TagDTO;

public interface PostService {

    void register(String id, PostDTO postDTO);

    List<PostDTO> getMyProducts(int accountId);

    void updateProducts(PostDTO postDTO);

    void deleteProduct(int userId, int productId);

    void registerComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deletePostComment(int userId, int commentId);

    void registerTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deletePostTag(int userId, int tagId);
}
