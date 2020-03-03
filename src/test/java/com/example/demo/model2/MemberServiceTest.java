package com.example.demo.model2;

import com.example.demo.domain.model2.member.Member;
import com.example.demo.domain.model2.member.MemberRepository;
import com.example.demo.domain.model2.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberServiceTest extends Model2Test {

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
