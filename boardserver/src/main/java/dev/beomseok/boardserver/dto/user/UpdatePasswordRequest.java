package dev.beomseok.boardserver.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePasswordRequest {
    @NotBlank(message = "이전 비밀번호가 비어 있습니다.")
    private String beforePassword;
    @NotBlank(message = "이후 비밀번호가 비어 있습니다.")
    private String afterPassword;
}