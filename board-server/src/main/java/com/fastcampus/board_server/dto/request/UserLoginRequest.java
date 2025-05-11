package com.fastcampus.board_server.dto.request;

import lombok.NonNull;

public record UserLoginRequest(
    @NonNull
    String userId,
    @NonNull
    String password
) {
    
}
