package dev.beomseok.boardserver.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class UserUpdatePasswordRequest {
    @NotBlank(message = "이전 비밀번호가 비어 있습니다.")
    private String beforePassword;
    @NotBlank(message = "이후 비밀번호가 비어 있습니다.")
    private String afterPassword;
}