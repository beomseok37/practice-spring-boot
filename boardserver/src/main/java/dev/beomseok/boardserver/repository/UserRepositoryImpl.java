package dev.beomseok.boardserver.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.beomseok.boardserver.domain.User;
import dev.beomseok.boardserver.dto.QUserDTO;
import dev.beomseok.boardserver.dto.UserDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import static dev.beomseok.boardserver.domain.QUser.*;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public UserDTO findByUserIdAndPassword(String userId, String password) {
        return queryFactory.select(new QUserDTO(
                        user.userId,
                        user.nickname,
                        user.status))
                .from(user)
                .where(user.userId.eq(userId), user.password.eq(password))
                .fetchOne();
    }

    @Override
    public User findAllInfoByUserIdAndPassword(String userId, String password) {
        return queryFactory.selectFrom(user)
                .where(user.userId.eq(userId), user.password.eq(password))
                .fetchOne();
    }


    @Override
    public UserDTO findByUserId(String userId) {
        return queryFactory.select(new QUserDTO(
                        user.userId,
                        user.nickname,
                        user.status))
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne();
    }
}
