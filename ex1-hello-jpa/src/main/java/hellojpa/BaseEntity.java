package hellojpa;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    private LocalDateTime createTime;
    private String createUser;
    private  LocalDateTime updateTime;
    private String updateUser;
}
