package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import study.datajpa.entity.Member;

import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom{
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        List<Member> findMembers = em.createQuery("select m from Member m", Member.class).getResultList();
        return findMembers;
    }
}
