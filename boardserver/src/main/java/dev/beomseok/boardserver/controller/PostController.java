package dev.beomseok.boardserver.controller;

import dev.beomseok.boardserver.dto.PostDTO;
import dev.beomseok.boardserver.dto.request.PostRequest;
import dev.beomseok.boardserver.service.PostService;
import dev.beomseok.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
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
    public void registerPost(HttpSession session, @RequestBody PostRequest postRequest) {
        String userId = SessionUtil.getLoginUserId(session);
        postService.registerPost(userId, postRequest);
    }

    @GetMapping
    public List<PostDTO> postInfo(HttpSession session) {
        String userId = SessionUtil.getLoginUserId(session);
        List<PostDTO> posts = postService.getPosts(userId);
        return posts;
    }

    @PatchMapping("{postId}")
    public void updatePost(HttpSession session, @PathVariable("postId") Long postId, @RequestBody PostRequest postRequest) {
        String userId = SessionUtil.getLoginUserId(session);
        postService.updatePost(userId, postId, postRequest);
    }

    @DeleteMapping("{postId}")
    public void updatePost(HttpSession session, @PathVariable("postId") Long postId) {
        String userId = SessionUtil.getLoginUserId(session);
        postService.deletePost(userId, postId);
    }

}
