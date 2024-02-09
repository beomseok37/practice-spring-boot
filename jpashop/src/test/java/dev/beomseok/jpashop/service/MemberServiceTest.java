package dev.beomseok.jpashop.service;

import dev.beomseok.jpashop.domain.Member;
import dev.beomseok.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원등록() throws Exception {
        //given
        Member member = new Member();
        member.setName("member1");

        //when
        Long joinId = memberService.join(member);

        //then
        assertEquals(member,memberRepository.findOne(joinId));
    }

    @Test
    public void 중복_회원_조회() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("member");
        Member member2 = new Member();
        member2.setName("member");

        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class,()->{
            memberService.join(member2);
        });
    }
}