package com.fastcampus.board_server.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.fastcampus.board_server.dto.TagDTO;

@Mapper
public interface TagMapper {
    public int register(TagDTO tagDTO);

    public void updateTags(TagDTO tagDTO);

    public void deletePostTag(int tagId);

    public void createPostTag(Integer tagId, Integer postId);

}
