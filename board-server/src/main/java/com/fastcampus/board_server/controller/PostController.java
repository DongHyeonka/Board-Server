package com.fastcampus.board_server.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.board_server.aop.LoginCheck;
import com.fastcampus.board_server.dto.CommentDTO;
import com.fastcampus.board_server.dto.PostDTO;
import com.fastcampus.board_server.dto.TagDTO;
import com.fastcampus.board_server.dto.UserDto;
import com.fastcampus.board_server.dto.response.CommonResponse;
import com.fastcampus.board_server.service.PostService;
import com.fastcampus.board_server.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/posts")
@Log4j2
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPost(
        String accountId, @RequestBody PostDTO postDTO
    ) {
        postService.register(accountId, postDTO);
        CommonResponse<PostDTO> commonResponse = new CommonResponse<>(OK, "SUCCESS", "registerPost", postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> myPostInfo(String accountId) {
        UserDto memberInfo = userService.getUserProfile(accountId);
        List<PostDTO> postDTOList = postService.getMyProducts(memberInfo.getId());
        CommonResponse<List<PostDTO>> commonResponse = new CommonResponse<>(OK, "SUCCESS", "myPostInfo", postDTOList);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostRequest>> updatePosts(String accountId,
        @PathVariable(name = "postId") int postId,
        @RequestBody PostRequest postRequest
    ) {
        UserDto memberInfo = userService.getUserProfile(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(postRequest.getName())
                .contents(postRequest.getContents())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(memberInfo.getId())
                .fileId(postRequest.getFileId())
                .updateTime(new Date())
                .build();
        postService.updateProducts(postDTO);
        CommonResponse<PostRequest> commonResponse = new CommonResponse<>(OK, "SUCCESS", "updatePosts", postRequest);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deleteposts(
        String accountId,
        @PathVariable(name = "postId") int postId,
        @RequestBody PostDeleteRequest postDeleteRequest
    ) {
        UserDto memberInfo = userService.getUserProfile(accountId);
        postService.deleteProduct(memberInfo.getId(), postId);
        CommonResponse<PostDeleteRequest> commonResponse = new CommonResponse<>(OK, "SUCCESS", "deleteposts", postDeleteRequest);
        return ResponseEntity.ok(commonResponse);
    }

    // -------------- comments --------------

    @PostMapping("comments")
    @ResponseStatus(CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> registerPostComment(
        String accountId,
        @RequestBody CommentDTO commentDTO
    ) {
        postService.registerComment(commentDTO);
        CommonResponse<CommentDTO> commonResponse = new CommonResponse<>(OK, "SUCCESS", "registerPostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> updatePostComment(String accountId,
            @PathVariable(name = "commentId") int commentId,
            @RequestBody CommentDTO commentDTO) {
        UserDto memberInfo = userService.getUserProfile(accountId);
        if (memberInfo != null)
            postService.updateComment(commentDTO);
        CommonResponse<CommentDTO> commonResponse = new CommonResponse<>(OK, "SUCCESS", "updatePostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> deletePostComment(String accountId,
            @PathVariable(name = "commentId") int commentId,
            @RequestBody CommentDTO commentDTO) {
        UserDto memberInfo = userService.getUserProfile(accountId);
        if (memberInfo != null)
            postService.deletePostComment(memberInfo.getId(), commentId);
        CommonResponse<CommentDTO> commonResponse = new CommonResponse<>(OK, "SUCCESS", "deletePostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    // -------------- tags --------------
    @PostMapping("tags")
    @ResponseStatus(CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> registerPostTag(
        String accountId, 
        @RequestBody TagDTO tagDTO
    ) {
        postService.registerTag(tagDTO);
        CommonResponse<TagDTO> commonResponse = new CommonResponse<>(OK, "SUCCESS", "registerPostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> updatePostTag(
        String accountId,
        @PathVariable(name = "tagId") int tagId,
        @RequestBody TagDTO tagDTO
    ) {
        UserDto memberInfo = userService.getUserProfile(accountId);
        if (memberInfo != null)
            postService.updateTag(tagDTO);
        CommonResponse<TagDTO> commonResponse = new CommonResponse<>(OK, "SUCCESS", "updatePostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> deletePostTag(
        String accountId,
        @PathVariable(name = "tagId") int tagId,
        @RequestBody TagDTO tagDTO
    ) {
        UserDto memberInfo = userService.getUserProfile(accountId);
        if (memberInfo != null)
            postService.deletePostTag(memberInfo.getId(), tagId);
        CommonResponse<TagDTO> commonResponse = new CommonResponse<>(OK, "SUCCESS", "deletePostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    // -------------- response 객체 --------------

    @Getter
    @AllArgsConstructor
    private static class PostResponse {
        private List<PostDTO> postDTO;
    }

    // -------------- request 객체 --------------

    @Setter
    @Getter
    private static class PostRequest {
        private String name;
        private String contents;
        private int views;
        private int categoryId;
        private int userId;
        private int fileId;
        private Date updateTime;
    }

    @Setter
    @Getter
    private static class PostDeleteRequest {
        private int id;
        private int accountId;
    }
}
