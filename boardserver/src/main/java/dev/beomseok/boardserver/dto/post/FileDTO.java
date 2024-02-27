package dev.beomseok.boardserver.dto.post;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FileDTO {
    private String name;
    private String path;
    private String extension;

    @QueryProjection
    public FileDTO(String name, String path, String extension) {
        this.name = name;
        this.path = path;
        this.extension = extension;
    }
}
