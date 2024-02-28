package dev.beomseok.boardserver.service.post;

import com.querydsl.core.NonUniqueResultException;
import dev.beomseok.boardserver.domain.Category;
import dev.beomseok.boardserver.domain.File;
import dev.beomseok.boardserver.domain.Post;
import dev.beomseok.boardserver.domain.User;
import dev.beomseok.boardserver.dto.post.FileDTO;
import dev.beomseok.boardserver.dto.post.PostDTO;
import dev.beomseok.boardserver.dto.post.PostRequest;
import dev.beomseok.boardserver.dto.post.PostSearch;
import dev.beomseok.boardserver.repository.CategoryRepository;
import dev.beomseok.boardserver.repository.FileRepository;
import dev.beomseok.boardserver.repository.post.PostRepository;
import dev.beomseok.boardserver.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
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
        return PostDTO.postToDTO(posts);
    }

    @Override
    @Cacheable(value = "getPosts", key = "'getPosts' + #postSearch.getTitle() + #postSearch.getCategoryId()")
    public List<PostDTO> getPosts(PostSearch postSearch) {
        List<Post> posts = postRepository.findBySearch(postSearch);
        return PostDTO.postToDTO(posts);
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
