package dev.beomseok.boardserver.dto;

import dev.beomseok.boardserver.domain.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagDTO {
    private String name;
    private String url;

    public TagDTO(Tag tag) {
        this.name = tag.getName();
        this.url = tag.getUrl();
    }
}
