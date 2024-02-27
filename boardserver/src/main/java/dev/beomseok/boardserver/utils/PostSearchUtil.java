package dev.beomseok.boardserver.utils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import dev.beomseok.boardserver.domain.Category.SortStatus;
import dev.beomseok.boardserver.dto.post.PostSearch;

import java.util.function.Supplier;

import static dev.beomseok.boardserver.domain.QPost.post;

public class PostSearchUtil {
    private static BooleanBuilder nullSafeBooleanBuilder(Supplier<BooleanExpression> supplier) {
        try {
            return new BooleanBuilder(supplier.get());
        } catch (RuntimeException e) {
            return new BooleanBuilder();
        }
    }

    private static BooleanBuilder idEq(Long idCond) {
        return nullSafeBooleanBuilder(() -> post.id.eq(idCond));
    }

    private static BooleanBuilder titleEq(String titleCond) {
        return nullSafeBooleanBuilder(() -> post.title.eq(titleCond));
    }

    private static BooleanBuilder contentContain(String content) {
        return nullSafeBooleanBuilder(() -> post.content.contains(content));
    }

    private static BooleanBuilder categoryIdEq(Long categoryIdCond) {
        return nullSafeBooleanBuilder(() -> post.category.id.eq(categoryIdCond));
    }

    private static BooleanBuilder userIdEq(Long userIdCond) {
        return nullSafeBooleanBuilder(() -> post.user.id.eq(userIdCond));
    }

    public static BooleanBuilder allCondition(PostSearch postSearch) {
        return idEq(postSearch.getId())
                .and(titleEq(postSearch.getTitle()))
                .and(categoryIdEq(postSearch.getCategoryId()))
                .and(userIdEq(postSearch.getUserId()))
                .and(contentContain(postSearch.getContent()));
    }

    public static OrderSpecifier orderCondition(SortStatus sortStatus) {
        if (sortStatus == null) {
            return post.createdDate.desc();
        } else if (sortStatus.toString().equals("CATEGORIES")) {
            return post.category.id.asc();
        } else if (sortStatus.toString().equals("NEWEST")) {
            return post.createdDate.desc();
        } else {
            return post.createdDate.asc();
        }
    }
}
