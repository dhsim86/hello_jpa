package com.dongho.dev.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void findAllTest() {

        List<Member> memberList;

        given: {

        }

        when: {
            memberList = memberRepository.findAll();
        }

        then: {
            assertThat(memberList).isNotEmpty().hasSize(2);
        }
    }

}
