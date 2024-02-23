package dev.beomseok.boardserver.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSignUpDto {
    @NotBlank(message = "ID가 비어 있습니다.")
    private String userId;

    @NotBlank(message = "비밀번호가 비어 있습니다.")
    private String password;

    @NotBlank(message = "닉네임이 비어 있습니다.")
    private String nickname;

    @NotNull(message = "admin 정보가 비어 있습니다.")
    private Boolean isAdmin;
}
