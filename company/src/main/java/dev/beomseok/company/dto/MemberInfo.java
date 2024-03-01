package dev.beomseok.company.dto;

import dev.beomseok.company.domain.Member;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberInfo {
    public static enum MemberRole {
        MANAGER,MEMBER
    }
    private String name;
    private String teamName;
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;
    private LocalDate birthday;
    private LocalDate workStartDate;

    public MemberInfo(Member member) {
        this.name = member.getMemberName();
        this.teamName = member.getTeam().getTeamName();
        this.role = member.getIsManager() ? MemberRole.MANAGER : MemberRole.MEMBER;
        this.birthday = member.getBirthday();
        this.workStartDate = member.getWorkStartDate();
    }
}
