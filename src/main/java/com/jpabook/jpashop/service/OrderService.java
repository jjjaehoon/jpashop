package com.jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Delivery;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;

	/**
	 * 주문
	 * 인자로 Entity를 넘기면 그 Entity는 JPA랑은 관계가 없는 것이다.
	 * 그래서 식별자를 넘겨서 JPA의 영속성 Entity를 가져와서 사용할것
	 */
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {

		//엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);

		//배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());

		//주문상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		//주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);

		/**
		 * cascade옵션이 있기 때문에
		 * Delivery와 OrderItem은 order를 persist할 때
		 * 같이 persist(insert)가 된다
		 */
		//주문 저장
		orderRepository.save(order);

		return order.getId();
	}

	/**
	 * 주문 취소
	 * JPA의 특징
	 * Entity의 데이터를 바꾸면
	 * 더티체킹(변경감지)해서 변경내역을 찾아 DB에 Update 쿼리를 날림
	 */
	@Transactional
	public void cancelOrder(Long orderId) {
		//주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);

		//주문 취소
		order.cancel();
	}

	/**
	 * 검색
	 */
	public List<Order> findOrders(OrderSearch orderSearch) {
		return orderRepository.findAllByCriteria(orderSearch);
	}
}
