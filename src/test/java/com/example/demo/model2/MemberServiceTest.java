package com.example.demo.model2;

import com.example.demo.domain.model2.member.Member;
import com.example.demo.domain.model2.member.MemberRepository;
import com.example.demo.domain.model2.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest(properties = {
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.jpa.properties.hibernate.use_sql_comments=true"
})
@ComponentScan({"com.example.demo.domain.model2"})
@EntityScan("com.example.demo.domain.model2")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    public void join() {
        Member member = new Member();
        member.setName("kim");

        Long id = memberService.join(member);

        Assertions.assertEquals(member, memberRepository.findOne(id));
    }

    @Test
    @DisplayName("중복 가입 체크")
    public void duplicateMember() {
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2), "예외 발생 !!");
    }
}
