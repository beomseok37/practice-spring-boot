package dev.beomseok.boardserver.service.user;

import dev.beomseok.boardserver.dto.user.UserDTO;
import dev.beomseok.boardserver.dto.user.SignUpRequest;

public interface UserService {

    void register(SignUpRequest userSignUpRequest);

    UserDTO login(String id, String password);

    boolean isDuplicatedId(String id);

    UserDTO getUserInfo(String userId);

    void updatePassword(String id, String beforePassword, String afterPassword);

    void delete(String id, String password);
}
