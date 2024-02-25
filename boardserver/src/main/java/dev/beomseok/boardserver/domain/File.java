package dev.beomseok.boardserver.domain;

import dev.beomseok.boardserver.dto.FileDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class File extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String path;

    private String extension;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //==생성자 메서드==//
    public File(FileDTO fileDTO, Post post) {
        this.name = fileDTO.getName();
        this.path = fileDTO.getPath();
        this.extension = fileDTO.getExtension();
        setPost(post);
    }

    //==수정자 메서드==//
    private void setPost(Post post) {
        this.post = post;
        post.getFiles().add(this);
    }
}
