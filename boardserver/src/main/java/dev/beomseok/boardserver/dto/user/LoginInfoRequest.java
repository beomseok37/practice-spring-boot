package dev.beomseok.boardserver.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginInfoRequest {
    @NotBlank(message = "ID가 비어 있습니다.")
    private String userId;

    @NotBlank(message = "비밀번호가 비어 있습니다.")
    private String password;
}