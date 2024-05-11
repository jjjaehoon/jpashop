package com.jpabook.jpashop.repository;

import org.springframework.stereotype.Repository;

import com.jpabook.jpashop.domain.Order;

import jakarta.persistence.EntityManager;
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

	//public List<Order> findAll(OrderSearch orderSearch)
}
