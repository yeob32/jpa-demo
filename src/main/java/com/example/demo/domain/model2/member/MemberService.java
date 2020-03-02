package com.example.demo.domain.model2.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {
        validateDuplicationMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicationMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
