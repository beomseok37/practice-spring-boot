package dev.beomseok.boardserver.domain;

import dev.beomseok.boardserver.dto.request.UserSignUpDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter
public class User extends BaseEntity{
    public static enum UserStatus{
        DEFAULT, ADMIN, DELETED
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
    public static User createUser(UserSignUpDto userSignUpDto){
        User user = new User();
        user.setUserId(userSignUpDto.getUserId());
        user.setPassword(userSignUpDto.getPassword());
        user.setNickname(userSignUpDto.getNickname());
        user.setStatus(userSignUpDto.getIsAdmin() ? UserStatus.ADMIN : UserStatus.DEFAULT);

        return user;
    }
}
