package dev.beomseok.boardserver.dto.response;

import dev.beomseok.boardserver.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private UserDTO userDTO;
}