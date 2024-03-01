package dev.beomseok.company.repository;

import dev.beomseok.company.domain.Member;
import dev.beomseok.company.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long> {

    @Query("select t from Team t join fetch t.members")
    List<Team> findAllByFetchJoin();
}
