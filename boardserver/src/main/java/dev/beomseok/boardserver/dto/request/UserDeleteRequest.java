package dev.beomseok.boardserver.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserDeleteRequest {
    @NonNull
    private String userId;

    @NonNull
    private String password;
}
