package dev.beomseok.boardserver.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginInfoRequest {
    @NotBlank(message = "ID가 비어 있습니다.")
    private String userId;

    @NotBlank(message = "비밀번호가 비어 있습니다.")
    private String password;
}