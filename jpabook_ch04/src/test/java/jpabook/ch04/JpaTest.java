package jpabook.ch04;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JpaTest {

    private static EntityManagerFactory entityManagerFactory;

    private Board board1;
    private Board board2;

    @BeforeAll
    public void beforeAll() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpabook");
    }

    @AfterAll
    public void afterAll() {
        entityManagerFactory.close();
    }

    @BeforeEach
    public void beforeEach() {
        board1 = new Board();
        board1.setData("test1");

        board2 = new Board();
        board2.setData("test2");
    }

    @Test
    @DisplayName("GeneratedValue - Identity 테스트")
    public void generatedValueIdentityTest() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        em.persist(board1);
        em.persist(board2);

        List<Board> boardList = em.createQuery("select b from Board b", Board.class).getResultList();
        boardList.stream().forEach(em::remove);

        tx.commit();
        em.close();

        assertThat(board1.getId()).isNotNull();
        assertThat(board2.getId()).isNotNull();
        assertThat(boardList).isNotEmpty();
        assertThat(boardList).extracting("id").containsExactlyInAnyOrder(board1.getId(), board2.getId());
    }

}
