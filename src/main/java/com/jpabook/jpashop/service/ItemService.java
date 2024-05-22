package com.jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public Long saveItem(Item item) {
		itemRepository.save(item);
		return item.getId();
	}
	/**
	 * 변경감지 = dirty checking
	 * Entity의 속성을 바꾸고 트랜잭션 커밋이 될 때
	 * JPA가 찾아서 자동으로 update 쿼리 생성해 DB에 반영
	 *
	 * 준영속 엔티티
	 * JPA의 영속성 컨텍스트가 관리하지 않는 엔티티
	 * 식별자를 가지고 있으면 준영속 엔티티
	 *
	 * 인자로 넘어온 Book은 준영속 엔티티지만
	 * 이미 만들어진 ID를 통해 엔티티를 찾아오면 findItem은 영속 엔티티기 때문에
	 * 변경감지가 가능함
	 */
	@Transactional
	public void updateItem(Long itemId, String name, int price, int stockQuantity) {
		Item findItem = itemRepository.findOne(itemId);

		//findItem.change(price, name, stockQuantity);
		findItem.setName(name);
		findItem.setPrice(price);
		findItem.setStockQuantity(stockQuantity);

		/**
		 * 위의 set 말고 change처럼 의미있는 메서드를 만들어야 한다.
		 * 아니면 DTO를 만들어서 데이터를 명확하게 전달하는게 중요
		 */
	}

	public List<Item> findItem() {
		return itemRepository.findAll();
	}

	public Item findOne(Long itemId) {
		return itemRepository.findOne(itemId);
	}
}
