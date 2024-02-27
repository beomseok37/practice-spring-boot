package dev.beomseok.boardserver.controller;

import dev.beomseok.boardserver.aop.LoginCheck;
import dev.beomseok.boardserver.dto.PostDTO;
import dev.beomseok.boardserver.dto.request.PostRequest;
import dev.beomseok.boardserver.service.PostService;
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

}
