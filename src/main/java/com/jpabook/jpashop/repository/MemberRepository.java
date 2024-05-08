package com.jpabook.jpashop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jpabook.jpashop.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	/**
	 * 	@Repository 안에 @Component 어노테이션이 있기 때문에 컴포넌트 스캔의 대상이 된다.
	 * 	그래서 spring bean으로 자동 등록이 된다.
	 *
	 * @PersistenceContext 어노테이션이 있으면 spring이 생성한 EM을 주입해준다.
	 *
	 * spring boot의 spring data jpa를 쓰면 @PersistenceContext-> @Autowired로 변경 가능
	 */
	private final EntityManager em;

	//Member 저장 로직
	public void save(Member member) {
		em.persist(member);
	}

	//Member 단건 조회
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}

	//Member 다건 조회
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class)
			.getResultList();
	}

	//Member 이름으로 조회
	public List<Member> findByName(String name) {
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
			.setParameter("name", name)
			.getResultList();
	}
}
