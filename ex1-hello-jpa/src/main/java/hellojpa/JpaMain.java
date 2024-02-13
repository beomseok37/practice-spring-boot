package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Team team = new Team();
            team.setName("team");

            Member member = new Member();
            member.setName("member1");
            team.addMember(member);
            em.persist(team);

            em.flush();
            em.clear();

            Member member1 = em.find(Member.class, member.getId());
            Team team1 = em.find(Team.class, team.getId());
            System.out.println("member1 = " + team1.getMembers().get(0));
            team1.getMembers().stream().forEach(System.out::println);


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
