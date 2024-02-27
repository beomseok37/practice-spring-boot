package dev.beomseok.boardserver.repository;

import dev.beomseok.boardserver.domain.User;
import dev.beomseok.boardserver.dto.user.UserDTO;

public interface UserRepositoryCustom {
    public UserDTO findByUserIdAndPassword(String userId, String password);
    public User findAllInfoByUserIdAndPassword(String userId, String password);

    public UserDTO findByUserId(String userId);

}
