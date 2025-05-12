package com.fastcampus.board_server.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.fastcampus.board_server.dto.CommentDTO;

@Mapper
public interface CommentMapper {
    public int register(CommentDTO commentDTO);

    public void updateComments(CommentDTO commentDTO);

    public void deletePostComment(int commentId);
}
