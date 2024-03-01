package dev.beomseok.company.repository;

import dev.beomseok.company.domain.DayOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DayOffRepository extends JpaRepository<DayOff,Long> {

    @Query("select d from DayOff d where d.member.id=:memberId and d.startDate >= :yearMonth")
    List<DayOff> findByMemberIdAndYearMonth(@Param("memberId") Long memberId,
                                            @Param("yearMonth")LocalDate yearMonth);
}
