package dev.beomseok.company.repository;

import dev.beomseok.company.domain.Work;
import dev.beomseok.company.dto.OverTimeWorkInfo;
import dev.beomseok.company.dto.TotalWorkTimeDto;
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

    @Query("select new dev.beomseok.company.dto.TotalWorkTimeDto(w.member.id, w.member.memberName, sum(timestampdiff(MINUTE,w.workStart,w.workEnd))) " +
            "from Work w " +
            "where date_format(w.workStart,'%Y-%m')=date_format(:yearMonth,'%Y-%m') " +
            "group by w.member.id")
    List<TotalWorkTimeDto> findByMemberIdAndDate(@Param("yearMonth") LocalDate yearMonth);
}
