package dev.beomseok.boardserver.controller;

import dev.beomseok.boardserver.aop.LoginCheck;
import dev.beomseok.boardserver.dto.comment.CommentRequest;
import dev.beomseok.boardserver.dto.post.PostDTO;
import dev.beomseok.boardserver.dto.post.PostRequest;
import dev.beomseok.boardserver.dto.post.PostSearch;
import dev.beomseok.boardserver.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.MEMBER)
    public void registerPost(String userId, @RequestBody PostRequest postRequest) {
        postService.registerPost(userId, postRequest);
    }

    @GetMapping
    @LoginCheck(type = LoginCheck.UserType.MEMBER)
    public List<PostDTO> postInfo(String userId) {
        List<PostDTO> posts = postService.getPosts(userId);
        return posts;
    }

    @PostMapping("/search")
    public List<PostDTO> searchPosts(@RequestBody PostSearch postSearch) {
        return postService.getPosts(postSearch);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.MEMBER)
    public void updatePost(String userId, @PathVariable("postId") Long postId, @RequestBody PostRequest postRequest) {
        postService.updatePost(userId, postId, postRequest);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.MEMBER)
    public void updatePost(String userId, @PathVariable("postId") Long postId) {
        postService.deletePost(userId, postId);
    }

    // comment

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.MEMBER)
    public void registerComment(String userId, @RequestBody CommentRequest request) {
        postService.registerComment(request);
    }

    @PatchMapping("/comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.MEMBER)
    public void updateComment(String userId, @PathVariable("commentId") Long commentId, @RequestBody CommentRequest request) {
        postService.updateComment(commentId, request);
    }

    @DeleteMapping("/comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.MEMBER)
    public void deleteComment(String userId, @PathVariable("commentId") Long commentId) {
        postService.deleteComment(commentId);
    }
}
