package com.group.libraryapp.domain.user;

import javax.persistence.*;

@Entity
public class UserLoanHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isReturn;

    private String bookName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    protected UserLoanHistory() {
    }

    public UserLoanHistory(User user, String bookName) {
        this.isReturn = false;
        this.user = user;
        this.bookName = bookName;
    }

    public void doReturn() {
        this.isReturn = true;
    }

    public String getBookName() {
        return bookName;
    }
}
