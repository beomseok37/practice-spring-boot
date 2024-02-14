package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
//            for(int i=0;i<100;i++){
//                Member member = new Member();
//                member.setName("member"+i);
//                em.persist(member);
//            }
//
//            em.flush();
//            em.clear();
//
//            String jpql = "select m from Member m order by m.name desc";
//            List<Member> result = em.createQuery(jpql, Member.class)
//                    .setFirstResult(0)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            result.stream().forEach((member -> System.out.println("member.getName() = " + member.getName())));
            Member member = new Member();
            member.setName("member1");
            member.setAddress(new Address("city","street","zipcode"));
            em.persist(member);

            em.flush();
            em.clear();

            String jpql = "select m.address from Member m";
            List<Address> resultList = em.createQuery(jpql, Address.class).getResultList();
            
            resultList.stream().forEach((address -> System.out.println("address.getCity() = " + address.getCity())));
            tx.commit();
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
