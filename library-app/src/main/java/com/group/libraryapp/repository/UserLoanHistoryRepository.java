package com.group.libraryapp.repository;

import com.group.libraryapp.domain.user.UserLoanHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory, Long> {

    boolean existsByBookNameAndIsReturn(String bookName, boolean isReturn);
}
