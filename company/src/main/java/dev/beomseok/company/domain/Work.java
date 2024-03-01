package dev.beomseok.company.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Work {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime workStart;

    private LocalDateTime workEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Work(){}

    public Work(Member member){
        this.workStart = LocalDateTime.now();
        this.member = member;
    }

    public void endWork(){
        workEnd = LocalDateTime.now();
    }
}
