package dev.beomseok.jpashop.api;

import dev.beomseok.jpashop.domain.Member;
import dev.beomseok.jpashop.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    //등록
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long memberId = memberService.join(member);

        return new CreateMemberResponse(memberId);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        Long memberId = memberService.join(member);

        return new CreateMemberResponse(memberId);
    }

    @Data
    public static class CreateMemberRequest{
        private String name;
    }
    @Data
    @AllArgsConstructor
    public static class CreateMemberResponse{
        private Long id;
    }
    //수정
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id,request.getName());
        Member findMember = memberService.findMember(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }

    //조회
    @GetMapping("/api/v1/members")
    public List<Member> memberV1(){
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<Object> collect = findMembers.stream()
                .map(findmember -> new MemberDto(findmember.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }


}
