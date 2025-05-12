package com.fastcampus.board_server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.fastcampus.board_server.dto.PostDTO;
import com.fastcampus.board_server.dto.UserDto;
import com.fastcampus.board_server.mapper.PostMapper;
import com.fastcampus.board_server.mapper.UserMapper;
import com.fastcampus.board_server.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserMapper userMapper;
    
    @CacheEvict(value = "getProducts", allEntries = true)
    @Override
    public void register(String id, PostDTO postDTO) {
        UserDto memberInfo = userMapper.getUserProfile(id);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if (memberInfo == null) {
            log.error("register ERROR! {}", postDTO);
            throw new RuntimeException("register ERROR! 상품 등록 메서드를 확인해주세요\n" + "Params : " + postDTO);
        }

        postMapper.register(postDTO);
    }

    @Override
    public List<PostDTO> getMyProducts(int accountId) {
        List<PostDTO> postDTOList = postMapper.selectMyProducts(accountId);
        return postDTOList;
    }

    @Override
    public void updateProducts(PostDTO postDTO) {
        if (postDTO != null && postDTO.getId() != 0 && postDTO.getUserId() != 0) {
            postMapper.updateProducts(postDTO);
        } else {
            log.error("updateProducts ERROR! {}", postDTO);
            throw new RuntimeException("updateProducts ERROR! 물품 변경 메서드를 확인해주세요\n" + "Params : " + postDTO);
        }
    }

    @Override
    public void deleteProduct(int userId, int productId) {
        if (userId != 0 && productId != 0) {
            postMapper.deleteProduct(productId);
        } else {
            log.error("deleteProudct ERROR! {}", productId);
            throw new RuntimeException("updateProducts ERROR! 물품 삭제 메서드를 확인해주세요\n" + "Params : " + productId);
        }
    }
}
