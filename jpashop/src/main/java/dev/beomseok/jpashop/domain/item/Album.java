package dev.beomseok.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue(value = "A")
@Getter
public class Album extends Item{
    private String artist;
    private String etc;
}
