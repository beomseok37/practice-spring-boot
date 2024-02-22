package dev.beomseok.boardserver.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSignUpDto {
    private String userId;
    private String password;
    private String nickname;
    private Boolean isAdmin;

    public static boolean hasNullDataBeforeSignup(UserSignUpDto userSignUpDto){
        return userSignUpDto.getUserId() == null || userSignUpDto.getPassword() == null
                || userSignUpDto.getNickname() == null || userSignUpDto.getIsAdmin() == null;
    }
}
