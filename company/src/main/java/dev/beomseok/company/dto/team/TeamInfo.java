package dev.beomseok.company.dto.team;

import dev.beomseok.company.domain.Member;
import dev.beomseok.company.domain.Team;
import lombok.Getter;

@Getter
public class TeamInfo {
    private String name;
    private String manager;
    private int memberCount;

    public TeamInfo(Team team) {
        this.name = team.getTeamName();
        Member manager = team.getMembers().stream().filter(member -> member.getIsManager())
                .findFirst().orElse(null);
        if(manager != null) {
            this.manager = manager.getMemberName();
        }
        this.memberCount = team.getMembers().size();
    }
}
