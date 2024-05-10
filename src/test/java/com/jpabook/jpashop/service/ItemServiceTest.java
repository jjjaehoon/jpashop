package com.jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.item.Album;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class ItemServiceTest {

	@Autowired ItemRepository itemRepository;
	@Autowired ItemService itemService;	
	@Autowired EntityManager em;

	@Test
	@Rollback(false)
	public void 상품추가 () throws Exception {
	    //given
		Album album = new Album();
		album.setArtist("Jung");

		//when
		Long saveId = itemService.saveItem(album);

	    //then
		assertEquals(album, itemService.findOne(saveId));

	}

	@Test
	public void 재고_증가 () throws Exception {
	    //given
		Album album = new Album();
		album.addStock(10);

	    //when
		Item saveItem = itemService.findOne(itemService.saveItem(album));

	    //then
		assertEquals(saveItem.getStockQuantity(), 10);

		album.addStock(2);
		assertEquals(saveItem.getStockQuantity(), 12);
	}
	
	@Test
	@Rollback(false)
	public void 재고_감소 () throws Exception {
	    //given
		Album album = new Album();
		album.addStock(10);
	    
	    //when
		album.removeStock(2);
		Item saveItem = itemService.findOne(itemService.saveItem(album));
	    
	    //then
		assertEquals(saveItem.getStockQuantity(), 8);
	}
}