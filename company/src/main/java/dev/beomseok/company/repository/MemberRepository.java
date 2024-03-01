package dev.beomseok.company.repository;

import dev.beomseok.company.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m join fetch m.team where m.team.id = :teamId")
    List<Member> findByTeamId(@Param("teamId") long teamId);

}
