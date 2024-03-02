package dev.beomseok.company.controller;

import dev.beomseok.company.dto.member.MemberInfo;
import dev.beomseok.company.dto.team.TeamCreateRequest;
import dev.beomseok.company.dto.team.TeamInfo;
import dev.beomseok.company.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public void createTeam(@RequestBody TeamCreateRequest request) {
        teamService.saveTeam(request);
    }

    @GetMapping
    public List<TeamInfo> getTeamInfos() {
        return teamService.getAllTeamInfo();
    }

    @GetMapping("/{teamId}")
    public List<MemberInfo> getMemberInfoInTeam(@PathVariable long teamId) {
        return teamService.getMemberInfos(teamId);
    }
}
