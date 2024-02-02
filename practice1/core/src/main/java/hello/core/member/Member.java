package hello.core.member;

public class Member {
    private Long id;
    private String name;
    private Grade grade;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Member(Long id, String name, Grade grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public Grade getGrade() {
        return grade;
    }
}
