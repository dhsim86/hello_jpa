package jpabook.ch03;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JpaTest {

    private static Logger logger = LoggerFactory.getLogger(JpaTest.class);

    private EntityManagerFactory entityManagerFactory;

    private Member member;

    @BeforeAll
    public void initEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpabook");
    }

    @AfterAll
    public void destroyEntityManagerFactory() {
        entityManagerFactory.close();
    }

    @BeforeEach
    public void initFixture() {
        member = new Member();
        member.setId("id1");
        member.setUsername("dongho");
        member.setAge(35);
    }

    @Test
    @DisplayName("객체 동등성 테스트")
    public void identityTest() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        em.persist(member);

        Member member1 = em.find(Member.class, member.getId());
        Member member2 = em.find(Member.class, member.getId());

        em.remove(member);

        tx.commit();
        em.close();

        assertThat(member1).isEqualTo(member2);
    }

    @Test
    @DisplayName("준영속 테스트 1 - Insert")
    public void detachAndInsertTest() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        em.persist(member);
        em.detach(member);

        Member member1 = em.find(Member.class, member.getId());

        tx.commit();
        em.close();

        assertThat(member1).isNull();
    }

    @Test
    @DisplayName("준영속 테스트 2 - Update")
    public void detachAndUpdateTest() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(member);

        tx.commit();
        em.close();

        // update test
        em = entityManagerFactory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        Member findMember = em.find(Member.class, member.getId());
        findMember.setAge(20);
        em.clear();

        tx.commit();
        em.close();


        em = entityManagerFactory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        findMember = em.find(Member.class, member.getId());
        em.remove(findMember);

        tx.commit();
        em.close();

        assertThat(findMember.getAge()).isEqualTo(35);
    }

    @Test
    @DisplayName("준영속 테스트 3 - merge")
    public void mergeTest() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.persist(member);

        tx.commit();
        em.close();

        em = entityManagerFactory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        Member mergedMember = em.merge(member);
        mergedMember.setAge(20);

        tx.commit();
        em.close();

        // Not equal
        assertThat(mergedMember).isNotEqualTo(member);

        em = entityManagerFactory.createEntityManager();
        tx = em.getTransaction();
        tx.begin();

        Member findMember = em.find(Member.class, member.getId());
        em.remove(findMember);

        tx.commit();
        em.close();

        assertThat(findMember.getAge()).isEqualTo(20);
    }

}
