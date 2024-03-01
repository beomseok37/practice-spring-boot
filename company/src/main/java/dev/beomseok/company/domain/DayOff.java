package dev.beomseok.company.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class DayOff {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public int getCount(){
        return endDate.compareTo(startDate)+1;
    }

    protected DayOff(){}

    public DayOff(Member member,LocalDate startDate, LocalDate endDate) {
        this.member = member;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
