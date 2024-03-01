package dev.beomseok.company.domain;

import dev.beomseok.company.dto.TeamCreateRequest;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 20)
    private String teamName;

    private int dayOffApplyLimit;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public Team(){}

    public boolean isApplyingDayOffPossible(LocalDate startDate) {
        LocalDate limitDate = startDate.minusDays(this.getDayOffApplyLimit());
        return LocalDate.now().compareTo(limitDate) > 0;
    }
}
