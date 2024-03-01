package dev.beomseok.company.repository;

import dev.beomseok.company.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m join fetch m.team where m.team.id = :teamId")
    List<Member> findByTeamId(@Param("teamId") long teamId);

    @Query("select m from Member m join fetch m.works w " +
            "where m.id = :memberId and w.workEnd is null")
    Optional<Member> findWorkNotEndedById(@Param("memberId") long memberId);

    // day off
    @Query("select m from Member m left join fetch m.dayOffs d join fetch m.team t where m.id = :memberId")
    Optional<Member> findMemberForDayOff(@Param("memberId") long memberId);
}
