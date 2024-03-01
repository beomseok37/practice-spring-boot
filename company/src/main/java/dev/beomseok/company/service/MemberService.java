package dev.beomseok.company.service;

import dev.beomseok.company.domain.DayOff;
import dev.beomseok.company.domain.Member;
import dev.beomseok.company.domain.Team;
import dev.beomseok.company.domain.Work;
import dev.beomseok.company.dto.*;
import dev.beomseok.company.repository.DayOffRepository;
import dev.beomseok.company.repository.MemberRepository;
import dev.beomseok.company.repository.TeamRepository;
import dev.beomseok.company.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final WorkRepository workRepository;
    private final DayOffRepository dayOffRepository;

    @Transactional
    public void saveMember(MemberCreateRequest request) {
        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(IllegalArgumentException::new);

        Member member = new Member(request.getMemberName(), request.getIsManager(), request.getBirthday());
        member.setTeam(team);
        memberRepository.save(member);
    }

    @Transactional
    public void startWork(Long memberId) {
        if (workRepository.existsByMemberIdAndWorkEndIsNull(memberId)) {
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

    public WorkResponse<WorkInfo> getWorkInfosV1(Long memberId, LocalDateTime date) {
        List<Work> result = workRepository.findMemberWorkInfo(memberId, date);
        WorkResponse<WorkInfo> workResponse = new WorkResponse<>();

        result.forEach(work -> {
            long minutes = Duration.between(work.getWorkStart(), work.getWorkEnd()).toMinutes();
            workResponse.addMinutes(minutes);
            workResponse.addWorkInfo(new WorkInfo(work.getWorkStart().toLocalDate(),minutes));
        });

        return workResponse;
    }

    @Transactional
    public void registerDayOff(Long memberId, DayOffCreateRequest request) {
        // member의 남은 휴가 날짜 확인
        Member member = memberRepository.findMemberForDayOff(memberId)
                .orElseThrow(IllegalArgumentException::new);
        Team team = member.getTeam();

        int year = request.getStartDate().getYear();
        int count = request.getEndDate().compareTo(request.getStartDate());
        if (member.isDayOffLeft(year, count)) {
            throw new IllegalArgumentException("가능한 휴가 일수가 부족합니다.");
        }

        // team의 휴가 기한 남았는지 확인
        if (team.isApplyingDayOffPossible(request.getStartDate())) {
            throw new IllegalArgumentException("휴가 신청 기한이 지나갔습니다.");
        }

        // 휴가 신청
        member.applyDayOff(request.getStartDate(), request.getEndDate());
    }

    public WorkResponse<WorkInfoWithDayOff> getWorkInfosV2(Long memberId, LocalDateTime baseDate){
        List<Work> workResult = workRepository.findMemberWorkInfo(memberId, baseDate);
        List<DayOff> dayOffResult = dayOffRepository.findByMemberIdAndYearMonth(memberId,baseDate.toLocalDate());
        WorkResponse<WorkInfoWithDayOff> workResponse = new WorkResponse<>();

        workResult.forEach(work -> {
            long minutes = Duration.between(work.getWorkStart(), work.getWorkEnd()).toMinutes();
            workResponse.addMinutes(minutes);
            workResponse.addWorkInfo(new WorkInfoWithDayOff(work.getWorkStart().toLocalDate(),minutes,false));
        });

        dayOffResult.forEach(dayOff -> {
            for (LocalDate date = dayOff.getStartDate();date.isBefore(dayOff.getEndDate()); date = date.plusDays(1)){
                workResponse.addWorkInfo(new WorkInfoWithDayOff(date,0,true));
            }
            workResponse.addWorkInfo(new WorkInfoWithDayOff(dayOff.getEndDate(),0,true));
        });
        workResponse.sortDetail();

        return workResponse;
    }
}
