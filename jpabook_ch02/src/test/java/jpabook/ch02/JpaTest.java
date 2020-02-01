package jpabook.ch02;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JpaTest {

    private static Logger logger = LoggerFactory.getLogger(JpaTest.class);

    @Test
    public void test() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpabook");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            logic(entityManager);

            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }
        entityManagerFactory.close();
    }

    public void logic(EntityManager em) {

        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("dongho");
        member.setAge(35);

        em.persist(member);

        member.setAge(20);

        Member findMember = em.find(Member.class, id);
        assertThat(findMember).isNotNull();
        assertThat(findMember.getUsername()).isEqualTo("dongho");
        assertThat(findMember.getAge()).isEqualTo(20);

        List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList();
        assertThat(memberList).isNotEmpty().hasSize(1);

        em.remove(member);
        assertThat(em.find(Member.class, id)).isNull();
    }

}
