package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class JpaBaseEntity {
    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

//    @PrePersist
//    public void prePersist(){
//        LocalDateTime now = LocalDateTime.now();
//        createTime = now;
//        updateTime = now;
//    }
//
//    @PreUpdate
//    public void preUpdate(){
//        updateTime = LocalDateTime.now();
//    }
}
