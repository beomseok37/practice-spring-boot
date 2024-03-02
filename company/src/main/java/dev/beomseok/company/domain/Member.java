package dev.beomseok.company.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Work> works = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<DayOff> dayOffs = new ArrayList<>();

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
        this.works.add(new Work(this));
    }

    public void endWork(){
        LocalDate today = LocalDate.now();
        Work todayWork = this.works.stream().filter(work -> work.getWorkStart().toLocalDate().compareTo(today) == 0)
                .findFirst().orElseThrow(IllegalArgumentException::new);
        todayWork.endWork();
    }

    public boolean isDayOffLeft(int year,int count) {
        Integer dayOffCount = this.dayOffs.stream().map(dayOff -> dayOff.getCount()).collect(Collectors.toList())
                .stream().reduce(0, (prev, curr) -> prev + curr);

        return dayOffCount+count > (workStartDate.getYear()==year ? 11:15);
    }

    public void applyDayOff(LocalDate startDate, LocalDate endDate) {
        DayOff dayOff = new DayOff(this,startDate, endDate);
        this.dayOffs.add(dayOff);
    }
}
