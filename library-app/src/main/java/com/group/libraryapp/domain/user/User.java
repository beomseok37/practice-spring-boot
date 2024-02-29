package com.group.libraryapp.domain.user;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, name = "name")
    private String name;

    private Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistoryList = new ArrayList<>();

    protected User() {
    }

    public User(String name, Integer age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다.", name));
        }
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    //==수정자 메서드==//
    public void updateName(String name) {
        this.name = name;
    }

    public void loanBook(String bookName) {
        this.userLoanHistoryList.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName) {
        UserLoanHistory userLoanHistory = this.userLoanHistoryList.stream().filter(history -> bookName.equals(history.getBookName()))
                .findFirst().orElseThrow(IllegalArgumentException::new);

        userLoanHistory.doReturn();
    }
}
