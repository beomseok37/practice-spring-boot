package dev.beomseok.company.controller;

import dev.beomseok.company.dto.*;
import dev.beomseok.company.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public void registerMember(@RequestBody MemberCreateRequest request) {
        memberService.saveMember(request);
    }

    @PostMapping("/{memberId}")
    public void startWork(@PathVariable("memberId") Long memberId) {
        memberService.startWork(memberId);
    }

    @PatchMapping("/{memberId}")
    public void endWork(@PathVariable("memberId") Long memberId) {
        memberService.endWork(memberId);
    }

    @GetMapping("/v1/{memberId}")
    public WorkResponse<WorkInfo> getWorkInfosV1(@PathVariable("memberId") Long memberId,
                                                   @ModelAttribute YearMonthParameter yearMonth){
        return memberService.getWorkInfosV1(memberId, yearMonth.getLocalDateTime());
    }

    @PostMapping("/day-off/{memberId}")
    public void registerDayOff(@PathVariable("memberId") Long memberId,
                               @RequestBody DayOffCreateRequest request) {
        memberService.registerDayOff(memberId,request);
    }

    @GetMapping("/v2/{memberId}")
    public WorkResponse<WorkInfoWithDayOff> getWorkInfosV2(@PathVariable("memberId") Long memberId,
                                     @ModelAttribute YearMonthParameter yearMonth){
        return memberService.getWorkInfosV2(memberId, yearMonth.getLocalDateTime());
    }

    @GetMapping("/over-time")
    public List<OverTimeWorkInfo> getOverTimeWorkInfo(@ModelAttribute YearMonthParameter yearMonth){
        return memberService.getWorkOverTime(yearMonth.getLocalDate());
    }
}
