package com.fastcampus.board_server.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.board_server.aop.LoginCheck;
import com.fastcampus.board_server.dto.CategoryDTO;
import com.fastcampus.board_server.dto.SortStatus;
import com.fastcampus.board_server.service.CategoryService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/categories")
@Log4j2
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(CREATED)
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void registerCategory(String accountId, @RequestBody CategoryDTO categoryDTO) {
        categoryService.register(accountId, categoryDTO);
    }

    @PatchMapping("{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void updateCategories(String accountId,
        @PathVariable(name = "categoryId") int categoryId,
        @RequestBody CategoryRequest categoryRequest
    ) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryRequest.getName(), SortStatus.NEWEST, 10, 1);
        categoryService.update(categoryDTO);
    }

    @DeleteMapping("{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void updateCategories(String accountId,
        @PathVariable(name = "categoryId") int categoryId
    ) {
        categoryService.delete(categoryId);
    }

    // -------------- request 객체 --------------

    @Setter
    @Getter
    private static class CategoryRequest {
        private int id;
        private String name;
    }

}
