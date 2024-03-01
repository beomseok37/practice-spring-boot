package dev.beomseok.company.service;

import dev.beomseok.company.domain.Member;
import dev.beomseok.company.domain.Work;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

//@Component
@RequiredArgsConstructor
public class WorkInitService{
    private final InitClass initClass;


    @PostConstruct
    public void init() {
        initClass.init();
    }

    @Component
    static class InitClass{
        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init(){
            Member member = em.createQuery("select m from Member m where id=3", Member.class).getSingleResult();
            for(int i=1;i<32;i++){
                Work work = new Work(member, LocalDateTime.of(2024, 3, i, 9, 0),LocalDateTime.of(2024, 3, i, 17, 20));
                em.persist(work);
            }
        }
    }
}
