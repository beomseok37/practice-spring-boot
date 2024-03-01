package dev.beomseok.company.controller;

import dev.beomseok.company.dto.MemberCreateRequest;
import dev.beomseok.company.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public void registerMember(@RequestBody MemberCreateRequest request) {
        memberService.saveMember(request);
    }
}
