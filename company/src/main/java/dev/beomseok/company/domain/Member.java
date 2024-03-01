package dev.beomseok.company.domain;

import dev.beomseok.company.dto.MemberCreateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length=20)
    private String memberName;

    private Boolean isManager;

    @Column(nullable = false)
    @CreatedDate
    private LocalDate workStartDate;

    @Column(nullable = false)
    private LocalDate birthday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "member")
    private List<Work> works = new ArrayList<>();

    public Member(String memberName,boolean isManager, LocalDate birthday) {
        this.memberName = memberName;
        this.isManager = isManager;
        this.birthday = birthday;
    }

    public Member() {

    }

    public void setTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

    public void startWork() {

    }
}
