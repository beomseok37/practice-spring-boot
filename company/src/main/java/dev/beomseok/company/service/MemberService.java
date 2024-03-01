package dev.beomseok.company.service;

import dev.beomseok.company.domain.Member;
import dev.beomseok.company.domain.Team;
import dev.beomseok.company.domain.Work;
import dev.beomseok.company.dto.MemberCreateRequest;
import dev.beomseok.company.dto.WorkInfo;
import dev.beomseok.company.dto.WorkResponse;
import dev.beomseok.company.repository.MemberRepository;
import dev.beomseok.company.repository.TeamRepository;
import dev.beomseok.company.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final WorkRepository workRepository;

    @Transactional
    public void saveMember(MemberCreateRequest request) {
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(IllegalArgumentException::new);

        Member member = new Member(request.getMemberName(), request.getIsManager(),request.getBirthday());
        member.setTeam(team);
        memberRepository.save(member);
    }

    @Transactional
    public void startWork(Long memberId) {
        if(workRepository.existsByMemberIdAndWorkEndIsNull(memberId)){
            throw new IllegalArgumentException("아직 출근 중입니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);
        member.startWork();
    }

    @Transactional
    public void endWork(Long memberId) {
        Member member = memberRepository.findWorkNotEndedById(memberId)
                .orElseThrow(IllegalArgumentException::new);
        member.endWork();
    }

    public WorkResponse getWorkInfos(Long memberId, LocalDateTime date) {
        List<Work> result = workRepository.findMemberWorkInfo(memberId, date);
        return new WorkResponse(result);
    }
}
