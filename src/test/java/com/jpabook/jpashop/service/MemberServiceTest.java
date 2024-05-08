package com.jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest	//Spring 컨테이너 안에서 테스트를 돌릴 수 있게 해주는 어노테이션
@Transactional
class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	@Autowired EntityManager em;

	/**
	 * @Transactional만 사용하면 자동 롤백되기 때문에 insert문이 날아가지 않음
	 * -> @Rollback(false)를 사용하면 insert문이 날아가는걸 확인 가능, db에 데이터가 들어감
	 * -> @Rollback(false)을 지우고 @Autowired EntityManager em를 넣고
	 *    마지막에 em.flush();를 넣으면 insert문이 날아가는건 확인 가능,
	 *    하지만 메서드가 끝나고 롤백되기 때문에 db에 데이터는 안들어감
	 */
	@Test
	public void 회원가입() throws Exception {
	    //given
		Member member = new Member();
		member.setName("kim");
	    
	    //when
		Long savedId = memberService.join(member);

		//then
		//em.flush();
		assertEquals(member, memberRepository.findOne(savedId));
	}

	@Test
	public void 중복_회원_예외() throws Exception {
	    //given
		Member member1 = new Member();
		member1.setName("Kim");

		Member member2 = new Member();
		member2.setName("Kim");

	    //when
		memberService.join(member1);

	    //then
		assertThrows(IllegalStateException.class, () -> memberService.join(member2));
	}

}