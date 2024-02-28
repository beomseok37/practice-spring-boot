package dev.beomseok.boardserver.repository.user;

import dev.beomseok.boardserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    int countByUserId(String id);

    User findOneByUserId(String name);

}
