package dev.beomseok.company.service;

import dev.beomseok.company.domain.Member;
import dev.beomseok.company.domain.Team;
import dev.beomseok.company.dto.MemberInfo;
import dev.beomseok.company.dto.TeamCreateRequest;
import dev.beomseok.company.dto.TeamInfo;
import dev.beomseok.company.repository.MemberRepository;
import dev.beomseok.company.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveTeam(TeamCreateRequest request) {
        Team team = new Team(request.getName());
        teamRepository.save(team);
    }

    public List<TeamInfo> getAllTeamInfo() {
        List<Team> result = teamRepository.findAllByFetchJoin();
        return result.stream().map(TeamInfo::new)
                .collect(Collectors.toList());
    }

    public List<MemberInfo> getMemberInfos(long teamId) {
        List<Member> result = memberRepository.findByTeamId(teamId);
        return result.stream().map(MemberInfo::new)
                .collect(Collectors.toList());
    }
}
