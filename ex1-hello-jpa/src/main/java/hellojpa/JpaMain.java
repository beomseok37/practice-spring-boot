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
            Team team1 = new Team();
            Team team2 = new Team();
            team1.setName("teamA");
            team2.setName("teamB");

            Member member1 = new Member();
            Member member2 = new Member();
            Member member3 = new Member();
            member1.setName("member1");
            member2.setName("member2");
            member3.setName("member3");

            team1.getMembers().add(member1);
            team1.getMembers().add(member2);
            team2.getMembers().add(member3);
            member1.setTeam(team1);
            member2.setTeam(team1);
            member3.setTeam(team2);

            em.persist(team1);
            em.persist(team2);
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

            String jpql = "select t from Team t join fetch t.members";
            List<Team> result = em.createQuery(jpql, Team.class)
                    .getResultList();

            for (Team team : result) {
                System.out.println("team = " + team.getName());
                for (Member member : team.getMembers()) {
                    System.out.println("> member = " + member.getName());
                }
            }

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
