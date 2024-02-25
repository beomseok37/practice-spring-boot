package dev.beomseok.boardserver.service;

import com.querydsl.core.NonUniqueResultException;
import dev.beomseok.boardserver.domain.Category;
import dev.beomseok.boardserver.domain.File;
import dev.beomseok.boardserver.domain.Post;
import dev.beomseok.boardserver.domain.User;
import dev.beomseok.boardserver.dto.FileDTO;
import dev.beomseok.boardserver.dto.PostDTO;
import dev.beomseok.boardserver.dto.request.PostRequest;
import dev.beomseok.boardserver.repository.CategoryRepository;
import dev.beomseok.boardserver.repository.FileRepository;
import dev.beomseok.boardserver.repository.PostRepository;
import dev.beomseok.boardserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServieImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FileRepository fileRepository;

    @Override
    @Transactional
    public void registerPost(String userId, PostRequest request) {
        User user = userRepository.findOneByUserId(userId);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 카테고리가 없습니다."));

        Post post = Post.createPost(request, user, category);
        postRepository.save(post);

        List<File> files = request.getFiles().stream()
                .map(fileDTO -> new File(fileDTO, post)).collect(Collectors.toList());
        fileRepository.saveAll(files);
    }

    @Override
    public List<PostDTO> getPosts(String userId) {
        List<Post> posts = postRepository.findPosts(userId);
        return posts.stream().map(post -> {
            List<FileDTO> files = post.getFiles().stream()
                    .map(file -> new FileDTO(file.getName(), file.getPath(), file.getExtension()))
                    .collect(Collectors.toList());

            return new PostDTO(post.getUser().getUserId(), post.getTitle(), post.getContent(),
                    post.getCreatedDate(), post.getModifiedDate(), post.getViews(), files);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updatePost(String userId, Long postId, PostRequest request) {
        try {
            Post post = postRepository.findByPostId(postId);

            if (!post.getUser().getUserId().equals(userId)) {
                throw new IllegalArgumentException("포스트에 대한 권한이 존재하지 않습니다.");
            }

            post.update(request);
        } catch (NonUniqueResultException ex) {
            throw new IllegalArgumentException("해당 Id의 포스트가 없습니다.");
        }
    }

    @Override
    @Transactional
    public void deletePost(String userId, long postId) {
        try {
            Post post = postRepository.findByPostId(postId);

            if (!post.getUser().getUserId().equals(userId)) {
                throw new IllegalArgumentException("포스트에 대한 권한이 존재하지 않습니다.");
            }

            postRepository.delete(post);
        } catch (NonUniqueResultException ex) {
            throw new IllegalArgumentException("해당 Id의 포스트가 없습니다.");
        }

    }
}
