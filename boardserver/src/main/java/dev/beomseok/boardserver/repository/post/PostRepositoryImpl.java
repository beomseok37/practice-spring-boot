package dev.beomseok.boardserver.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.beomseok.boardserver.domain.Post;
import dev.beomseok.boardserver.domain.QComment;
import dev.beomseok.boardserver.domain.QTag;
import dev.beomseok.boardserver.dto.post.PostSearch;
import dev.beomseok.boardserver.utils.PostSearchUtil;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static dev.beomseok.boardserver.domain.QPost.post;
import static dev.beomseok.boardserver.domain.QTag.*;
import static dev.beomseok.boardserver.domain.QUser.user;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findPosts(String userId) {
        QComment parentComment = new QComment("parentComment");
        QComment childComment = new QComment("childComment");

        return queryFactory.selectFrom(post)
                .join(post.user)
                .leftJoin(post.files)
                .leftJoin(post.comments, parentComment)
                .leftJoin(childComment).on(parentComment.id.eq(childComment.parentComment.id))
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

    @Override
    public List<Post> findBySearch(PostSearch postSearch) {
        return queryFactory.selectFrom(post)
                .join(post.files)
                .join(post.user).fetchJoin()
                .where(PostSearchUtil.allCondition(postSearch))
                .orderBy(PostSearchUtil.orderCondition(postSearch.getSortStatus()))
                .fetch();
    }

    @Override
    public List<Post> findByTag(Long tagId) {
        return queryFactory.selectFrom(post)
                .join(post.user, user).fetchJoin()
                .join(post.tags, tag)
                .where(tag.id.eq(tagId))
                .fetch();
    }
}
