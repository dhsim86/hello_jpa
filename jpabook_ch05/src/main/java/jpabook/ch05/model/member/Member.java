package jpabook.ch05.model.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long Id;

    private String name;

    private String city;
    private String street;
    private String zipcode;

}
