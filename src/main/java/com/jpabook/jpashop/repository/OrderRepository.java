package com.jpabook.jpashop.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;

	/**
	 * 주문 저장
	 */
	public void save(Order order) {
		em.persist(order);
	}

	/**
	 * 주문 단일 조회
	 */
	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}

	/**
	 * 주문 검색 로직
	 * JPA의 Criteria라는 JPA에서 지원하는 방식으로 작성한 동적쿼리
	 * 유지보수성과 가독성이 너무 안좋아 실제로 사용하지 않는 방법
	 * 최고의 대안으로 QueryDsl이 있다.
	 * 강의가 JPA이기 때문에 이거로 진행하신듯 하다
	 */
	public List<Order> findAllByCriteria(OrderSearch orderSearch) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Order> o = cq.from(Order.class);
		Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
		List<Predicate> criteria = new ArrayList<>();
		//주문 상태 검색
		if (orderSearch.getOrderStatus() != null) {
			Predicate status = cb.equal(o.get("status"),
				orderSearch.getOrderStatus());
			criteria.add(status);
		}
		//회원 이름 검색
		if (StringUtils.hasText(orderSearch.getMemberName())) {
			Predicate name =
				cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName()
					+ "%");
			criteria.add(name);
		}
		cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
		TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000 건

		return query.getResultList();
	}
}
