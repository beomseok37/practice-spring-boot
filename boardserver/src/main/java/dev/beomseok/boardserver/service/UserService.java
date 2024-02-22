package dev.beomseok.boardserver.service;

import dev.beomseok.boardserver.dto.UserDTO;
import dev.beomseok.boardserver.dto.request.UserSignUpDto;

public interface UserService {

    void register(UserSignUpDto userSignUpDto);

    UserDTO login(String id, String password);

    boolean isDuplicatedId(String id);

    UserDTO getUserInfo(String userId);

    void updatePassword(String id, String beforePassword, String afterPassword);

    void delete(String id, String password);
}
