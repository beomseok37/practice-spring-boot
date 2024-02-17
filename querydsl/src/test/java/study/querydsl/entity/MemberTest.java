package study.querydsl.entity;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QTeam.team;

@SpringBootTest
@Transactional
class MemberTest {
    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void beforeEach(){
         queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    void testEntity(){
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member member : members) {
            System.out.println("member=" + member);
            System.out.println("-> member.team=" + member.getTeam());
        }
    }

    /**
     * find member1 by jpql
     * @throws Exception
     */
    @Test
    public void findMember1JPQL() throws Exception {
        Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(result.getAge()).isEqualTo(10);
    }

    /**
     * find member1 by querydsl
     * @throws Exception
     */
    @Test
    public void findMember1QueryDSL() throws Exception {

        Member result = queryFactory.selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(result.getAge()).isEqualTo(10);
    }

    @Test
    public void searchByQueryDSL() {
        Member result = queryFactory.selectFrom(member)
                .where(member.username.eq("member1"), member.age.eq(10))
                .fetchOne();

        assertThat(result.getUsername()).isEqualTo("member1");
    }

    @Test
    public void sort(){
        em.persist(new Member(null,100));
        em.persist(new Member("member5",100));
        em.persist(new Member("member6",100));

        List<Member> result = queryFactory.selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);

        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }

    @Test
    public void paging(){
        PageRequest pageRequest = PageRequest.of(1, 2);

        List<Member> result = queryFactory.selectFrom(member)
                .orderBy(member.age.desc())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        Member member3 = result.get(0);
        Member member4 = result.get(1);
        assertThat(result.size()).isEqualTo(2);
        for (Member member : result) {
            System.out.println("member = " + member);
        }
        assertThat(member3.getUsername()).isEqualTo("member2");
        assertThat(member4.getUsername()).isEqualTo("member1");
    }

    @Test
    public void groupBy(){
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();
        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);
        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);
        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }

    @Test
    public void join() {
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("member1","member2");
    }

    @Test
    public void theta_join() throws Exception {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        List<Member> result = queryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    @Test
    public void join_on_filtering() {
        List<Tuple> result = queryFactory.select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void fetchJoinUse() {
        Member result = queryFactory.selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(result.getTeam());
        assertThat(isLoaded).as("패치 조인 적용").isTrue();
    }

    @Test
    public void subQueryInWhere(){
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory.selectFrom(member)
                .where(member.age.goe(
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age").containsExactly(30,40);
    }

    @Test
    public void caseQuery(){
        List<String> result = queryFactory.select(
                        new CaseBuilder()
                                .when(member.age.between(0, 20)).then("0~20살")
                                .when(member.age.between(21, 30)).then("21살~30")
                                .otherwise("기타")
                ).from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void projectionOne(){
        List<String> result = queryFactory.select(member.username)
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void projectionDto(){
        QMember memberSub = new QMember("memberSub");

        // setter 활용
//        List<MemberDto> result = queryFactory.select(Projections.bean(MemberDto.class,
//                        member.username,
//                        member.age))
//                .from(member)
//                .fetch();

        // 필드 직접 접근
//        List<MemberDto> result = queryFactory.select(Projections.fields(MemberDto.class,
//                        member.username,
//                        member.age))
//                .from(member)
//                .fetch();

        // 필드 이름 다를 경우
//        List<MemberDto> result = queryFactory.select(Projections.fields(MemberDto.class,
//                        member.username.as("name"),
//                        ExpressionUtils.as(
//                                JPAExpressions
//                                        .select(memberSub.age.max())
//                                        .from(memberSub), "age")))
//                .from(member)
//                .fetch();

        // 생성자
//        List<MemberDto> result = queryFactory.select(Projections.constructor(MemberDto.class,
//                        member.username,
//                        member.age))
//                .from(member)
//                .fetch();

        // query projection
        List<MemberDto> result = queryFactory.select(new QMemberDto(member.username,member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println("memberDto = " + memberDto);
        }
    }

    @Test
    public void dynamicBooleanBuilder(){
        String usernameParam = "member1";
        Integer ageParam = null;

        List<Member> result = searchMember1(usernameParam, ageParam);

        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameParam, Integer ageParam) {
        BooleanBuilder builder = new BooleanBuilder();

        if(usernameParam!=null){
            builder.and(member.username.eq(usernameParam));
        }
        if(ageParam!=null){
            builder.and(member.age.eq(ageParam));
        }
        return queryFactory.selectFrom(member)
                .where(builder)
                .fetch();
    }

    @Test
    public void dynamicTestWhereParam(){
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember2(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameParam, Integer ageParam) {
        return queryFactory.selectFrom(member)
                .where(usernameEq(usernameParam),ageEq(ageParam))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameParam){
        return usernameParam!=null? member.username.eq(usernameParam):null;
    }

    private BooleanExpression ageEq(Integer ageParam){
        return ageParam != null ? member.age.eq(ageParam):null;
    }

    @Test
    public void bulkUpdate(){
        long count = queryFactory.update(member)
                .set(member.username, "메롱")
                .where(member.age.lt(25))
                .execute();

        em.flush();
        em.clear();

        List<Member> result = queryFactory.selectFrom(member)
                .where(member.username.eq("메롱"))
                .fetch();

        assertThat(result.size()).isEqualTo(2);
    }


    @Test
    public void sqlFunction(){
        List<String> result = queryFactory
                .select(Expressions.stringTemplate("function('replace', {0}, {1}, {2})",
                        member.username, "member", "M"))
                .from(member)
                .fetch();
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }
}