package dev.beomseok.boardserver.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpRequest extends LoginInfoRequest {
    @NotBlank(message = "닉네임이 비어 있습니다.")
    private String nickname;

    @NotNull(message = "admin 정보가 비어 있습니다.")
    private Boolean isAdmin;
}
