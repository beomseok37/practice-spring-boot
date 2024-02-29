package com.group.libraryapp.service;

import com.group.libraryapp.domain.book.Book;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.book.BookCreateRequest;
import com.group.libraryapp.dto.book.BookLoanRequest;
import com.group.libraryapp.dto.book.BookReturnRequest;
import com.group.libraryapp.repository.BookRepository;
import com.group.libraryapp.repository.UserLoanHistoryRepository;
import com.group.libraryapp.repository.user.UserJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final UserJpaRepository userRepository;
    private final UserLoanHistoryRepository userLoanHistoryRepository;

    public BookService(BookRepository bookRepository, UserJpaRepository userRepository, UserLoanHistoryRepository userLoanHistoryRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userLoanHistoryRepository = userLoanHistoryRepository;
    }

    @Transactional
    public void saveBook(BookCreateRequest request) {
        bookRepository.save(new Book(request.getName()));
    }

    @Transactional
    public void loanBook(BookLoanRequest request) {
        Book book = bookRepository.findByName(request.getBookName())
                .orElseThrow(IllegalArgumentException::new);

        if (userLoanHistoryRepository.existsByBookNameAndIsReturn(book.getName(), false)) {
            throw new IllegalArgumentException();
        }
        User user = userRepository.findAllByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        user.loanBook(book.getName());
    }

    @Transactional
    public void returnBook(BookReturnRequest request) {
        User user = userRepository.findAllByName(request.getUserName())
                .orElseThrow(IllegalArgumentException::new);

        user.returnBook(request.getBookName());
    }
}
