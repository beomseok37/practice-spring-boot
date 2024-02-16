package study.datajpa.controller;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members")
    public Page<Member> list(Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        return page;
    }

    @PostConstruct
    public void postConstruct(){
        for(int i=0;i<100;i++){
            memberRepository.save(new Member("member"+i,i));
        }
    }
}
