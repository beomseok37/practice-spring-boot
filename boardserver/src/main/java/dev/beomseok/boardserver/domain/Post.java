package dev.beomseok.boardserver.domain;

import dev.beomseok.boardserver.dto.post.PostRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Boolean isAdmin;
    private String content;
    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();

    private Post(String title, boolean isAdmin, String content, int views, User user, Category category) {
        this.title = title;
        this.isAdmin = isAdmin;
        this.content = content;
        this.views = views;
        this.user = user;
        this.category = category;
    }

    //==생성자 메서드==//
    public static Post createPost(PostRequest postRequest, User user, Category category) {
        return new Post(
                postRequest.getTitle(),
                postRequest.getIsAdmin(),
                postRequest.getContent(),
                0,
                user,
                category);
    }

    //==수정자 메서드==//
    public void update(PostRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
