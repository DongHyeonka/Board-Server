package com.fastcampus.board_server.dto.request;

import lombok.NonNull;

public record UserUpdatePasswordRequest(
    @NonNull
    String beforePassword,
    @NonNull
    String afterPassword
) {
    
}
