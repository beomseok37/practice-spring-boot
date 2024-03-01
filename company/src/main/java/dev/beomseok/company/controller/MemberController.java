package dev.beomseok.company.controller;

import dev.beomseok.company.dto.MemberCreateRequest;
import dev.beomseok.company.dto.WorkResponse;
import dev.beomseok.company.dto.YearMonthParameter;
import dev.beomseok.company.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;

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

    @GetMapping("/{memberId}")
    public WorkResponse getWorkInfos(@PathVariable("memberId") Long memberId,
                                     @ModelAttribute YearMonthParameter yearMonth){
        return memberService.getWorkInfos(memberId, yearMonth.getLocalDateTime());
    }
}
