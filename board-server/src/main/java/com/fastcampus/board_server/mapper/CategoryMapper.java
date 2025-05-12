package com.fastcampus.board_server.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.fastcampus.board_server.dto.CategoryDTO;

@Mapper
public interface CategoryMapper {
    public int register(CategoryDTO productDTO);

    public void updateCategory(CategoryDTO categoryDTO);

    public void deleteCategory(int categoryId);
}
