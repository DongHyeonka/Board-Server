package com.fastcampus.board_server.dto.request;

import lombok.NonNull;

public record UserDeleteId(
    @NonNull
    String id,
    @NonNull
    String password
) {
    
}
