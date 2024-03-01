package dev.beomseok.company.repository;

import dev.beomseok.company.domain.Member;
import dev.beomseok.company.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work,Long> {

    boolean existsByMemberIdAndWorkEndIsNull(@Param("memberId") long memberId);

    @Query("select w from Work w where w.member.id=:memberId and w.workStart >= :date order by w.workStart")
    List<Work> findMemberWorkInfo(@Param("memberId") Long memberId,
                                  @Param("date") LocalDateTime date);
}
