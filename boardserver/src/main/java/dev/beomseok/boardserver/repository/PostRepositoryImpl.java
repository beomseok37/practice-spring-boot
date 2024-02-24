package dev.beomseok.boardserver.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.beomseok.boardserver.domain.Post;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static dev.beomseok.boardserver.domain.QFile.file;
import static dev.beomseok.boardserver.domain.QPost.post;
import static dev.beomseok.boardserver.domain.QUser.user;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findPosts(String userId) {
        return queryFactory.selectFrom(post)
                .join(post.user, user).fetchJoin()
                .leftJoin(post.files, file).fetchJoin()
                .where(user.userId.eq(userId))
                .fetch();
    }

    @Override
    public Post findByPostId(Long postId) {
        return queryFactory.selectFrom(post)
                .join(post.user, user).fetchJoin()
                .where(post.id.eq(postId))
                .fetchOne();
    }
}
