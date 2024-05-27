package com.jpabook.jpashop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.jpabook.jpashop.domain.item.Book;
import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.service.ItemService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	/**
	 * 상품 등록 화면
	 */
	@GetMapping(value = "/items/new")
	public String createForm(Model model) {
		model.addAttribute("form", new BookForm());
		return "items/createItemForm";
	}

	/**
	 * 상품 등록
	 */
	@PostMapping(value = "/items/new")
	public String create(BookForm form) {
		Book book = new Book();
		book.setName(form.getName());
		book.setPrice(form.getPrice());
		book.setStockQuantity(form.getStockQuantity());
		book.setAuthor(form.getAuthor());
		book.setIsbn(form.getIsbn());
		itemService.saveItem(book);
		return "redirect:/";
	}

	/**
	 * 상품 목록 조회
	 */
	@GetMapping("/items")
	public String itemList(Model model) {
		List<Item> items = itemService.findItem();
		model.addAttribute("items", items);
		return "items/itemList";
	}

	/**
	 * 상품 수정 폼
	 */
	@GetMapping("items/{itemId}/edit")
	public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
		Book item = (Book)itemService.findOne(itemId);

		BookForm form = new BookForm();
		form.setId(item.getId());
		form.setName(item.getName());
		form.setPrice(item.getPrice());
		form.setStockQuantity(item.getStockQuantity());
		form.setAuthor(item.getAuthor());
		form.setIsbn(item.getIsbn());

		model.addAttribute("form", form);
		return "items/updateItemForm";
	}

	/**
	 * 상품 수정
	 * uri로 id를 다른 유저가 수정할 수 있기 때문에 아이템에 대한 유저 권한을 체크하는 로직이 있으면 좋음
	 */
	@PostMapping("items/{itemId}/edit")
	public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
		itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
		return "redirect:/items";
	}
}
