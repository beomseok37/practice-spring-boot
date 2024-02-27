package dev.beomseok.boardserver.domain;

import dev.beomseok.boardserver.dto.user.SignUpRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class User extends BaseEntity{
    public static enum UserStatus{
        MEMBER, ADMIN, DELETED
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private UserStatus status;

    //== 생성자 메서드 ==//
    public static User createUser(SignUpRequest userSignUpRequest){
        User user = new User();
        user.setUserId(userSignUpRequest.getUserId());
        user.setPassword(userSignUpRequest.getPassword());
        user.setNickname(userSignUpRequest.getNickname());
        user.setStatus(userSignUpRequest.getIsAdmin() ? UserStatus.ADMIN : UserStatus.MEMBER);

        return user;
    }
}
