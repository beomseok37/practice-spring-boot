package hellojpa;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(unique = true)
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
