package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private EntityManager em;


    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).get();
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername()); assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성 보장
    }

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll(); assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = memberRepository.count(); assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        List<Member> result =
                memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void findUser() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);
        List<Member> result = memberRepository.findUser("AAA", 20);
        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void findMemberDto(){
        Team teamA = new Team("teamA");
        teamRepository.save(teamA);
        Member m1 = new Member("AAA", 10);
        m1.setTeam(teamA);
        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();

        assertThat(memberDto.get(0).getTeamName()).isEqualTo(teamA.getName());
    }

    @Test
    public void findByUsernames() throws Exception {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);


        //when
        List<Member> members = memberRepository.findByUsernames(Arrays.asList("AAA", "BBB"));

        //then
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    public void findByUsername() throws Exception {
        //given
        Member member = new Member("member",10);
        memberRepository.save(member);

        em.flush();
        em.clear();
        //when
        Member member1 = memberRepository.findByUsername("memb2er");
        System.out.println("member1 = " + member1);
        //then
//        assertThat(member.getUsername()).isEqualTo(member1.getUsername());
    }

    @Test
    public void findPageable() throws Exception {
        //given
        Team team1 = new Team("teamA");
        Team team2 = new Team("teamB");
        teamRepository.save(team1);
        teamRepository.save(team2);

        Member member1 = new Member("member1",10);
        Member member2 = new Member("member2",10);
        Member member3 = new Member("member3",10);
        Member member4 = new Member("member4",10);
        Member member5 = new Member("member5",10);
        Member member6 = new Member("member6",10);
        member1.setTeam(team1);
        member2.setTeam(team1);
        member3.setTeam(team1);
        member4.setTeam(team1);
        member5.setTeam(team2);
        member6.setTeam(team2);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);
        memberRepository.save(member6);


        //when
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        Slice<Member> page = memberRepository.findPageable("teamA",pageRequest);

        //then

        List<Member> content = page.getContent();
        assertThat(content.size()).isEqualTo(3);
//        assertThat(page.getTotalElements()).isEqualTo(4);
        assertThat(page.getNumber()).isEqualTo(0);
//        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
    }
    
    @Test
    public void bulkAgePlus() throws Exception {
        //given
        Member member1 = new Member("member1",10);
        Member member2 = new Member("member2",20);
        Member member3 = new Member("member3",30);
        Member member4 = new Member("member4",20);
        Member member5 = new Member("member5",20);
        Member member6 = new Member("member6",20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);
        memberRepository.save(member6);
        
        //when
        memberRepository.bulkAgePlus(20);
        em.clear();
        //then
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println("member.getAge() = " + member.getAge());
        }
    }

    @Test
    public void findMemberEntityGraph() throws Exception {
        //given
        Team team1 = new Team("teamA");
        Team team2 = new Team("teamB");
        teamRepository.save(team1);
        teamRepository.save(team2);

        Member member1 = new Member("member1",10);
        Member member2 = new Member("member2",10);
        Member member3 = new Member("member3",10);
        Member member4 = new Member("member4",10);
        Member member5 = new Member("member5",10);
        Member member6 = new Member("member6",10);
        member1.setTeam(team1);
        member2.setTeam(team1);
        member3.setTeam(team1);
        member4.setTeam(team1);
        member5.setTeam(team2);
        member6.setTeam(team2);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);
        memberRepository.save(member6);
        em.flush();
        em.clear();
        //when

        List<Member> memberEntityGraphBy = memberRepository.findMemberEntityGraphBy();

        for (Member member : memberEntityGraphBy) {
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }

        //then
    }

    @Test
    public void findMemberCustom() throws Exception {
        //given
        Member member = new Member("member1",10);
        memberRepository.save(member);

        em.flush();
        em.clear();
        //when
        List<Member> members = memberRepository.findMemberCustom();

        //then
        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    public void auditing() throws Exception {
        //given
        Member member = new Member("member",10);
        memberRepository.save(member);
        //when
        member.setUsername("new member");

        em.flush();
        em.clear();
        //then
        Optional<Member> optionalMember = memberRepository.findById(1L);
        System.out.println("optionalMember.get().getCreateTime() = " + optionalMember.get().getCreateTime());
        System.out.println("optionalMember.get().getUpdateTime() = " + optionalMember.get().getUpdateTime());
        System.out.println("optionalMember.get().getCreatedBy() = " + optionalMember.get().getCreatedBy());
        System.out.println("optionalMember.get().getLastModifiedBy() = " + optionalMember.get().getLastModifiedBy());
    }

}