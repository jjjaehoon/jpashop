package com.jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)	//readOnly = true 옵션은 조회에서 성능 최적화
@RequiredArgsConstructor
public class MemberService {

	/**
	 * @Service 또한 자동으로 컴포넌트 스캔 대상임
	 * 데이터 변경에는 @Transactional가 있어야 함, Spring에서 제공하는 것으로 import할 것!!
	 *
	 * @Transactional(readOnly = true)가 필요한 로직이 더 많기 때문에 위로 빼서 나머지 적용시키고
	 * 아닌 로직은 따로 @Transactional 어노테이션을 주면 그게 우선 적용
	 */
	private final MemberRepository memberRepository;

	/**
	 * 회원 가입
	 */
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member);	//중복 회원 검증
		memberRepository.save(member);

		return member.getId();
	}

	/**
	 * ctrl + alt + v (변수 추출 단축키)
	 * 같은 이름의 member가 동시에 들어오면 둘 다 통과될 수 있기 때문에
	 * member의 name을 유니크 제약 조건으로 하는게 좋다
	 */
	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());

		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	//회원 전체 조회
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	//단건 조회
	public Member findOne(Long id) {
		return memberRepository.findOne(id);
	}
}
