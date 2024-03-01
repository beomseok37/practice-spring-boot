package dev.beomseok.company.service;

import dev.beomseok.company.domain.Member;
import dev.beomseok.company.domain.Team;
import dev.beomseok.company.dto.MemberCreateRequest;
import dev.beomseok.company.repository.MemberRepository;
import dev.beomseok.company.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void saveMember(MemberCreateRequest request) {
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(IllegalArgumentException::new);

        Member member = new Member(request.getMemberName(), request.getIsManager(),request.getBirthday());
        member.setTeam(team);
        memberRepository.save(member);
    }
}
