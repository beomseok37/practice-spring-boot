package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long>, MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String name, int age);

    @Query("select m from Member m where m.username=:username and m.age= :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select new study.datajpa.dto.MemberDto(m.id,m.username,t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :usernames")
    List<Member> findByUsernames(@Param("usernames") Collection<String> usernames);

    Member findByUsername(String username);

    @Query("select m from Member m join fetch m.team t where t.name = :teamName")
    Slice<Member> findPageable(@Param("teamName") String teamName, Pageable pageable);

    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age")int age);

    @EntityGraph(attributePaths = {"team"})
    List<Member> findMemberEntityGraphBy();
}