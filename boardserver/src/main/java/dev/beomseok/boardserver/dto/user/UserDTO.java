package dev.beomseok.boardserver.dto.user;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import static dev.beomseok.boardserver.domain.User.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String userId;
    private String nickName;
    private UserStatus status;

    @QueryProjection
    public UserDTO(String userId, String nickname, UserStatus status) {
        this.userId = userId;
        this.nickName = nickname;
        this.status = status;
    }
}
