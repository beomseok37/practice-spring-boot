package com.group.libraryapp.repository;

import com.group.libraryapp.domain.user.UserLoanHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {

    boolean existsByBookNameAndIsReturn(String bookName, boolean isReturn);

    Optional<UserLoanHistory> findByUserIdAndBookName(Long id, String bookName);
}
