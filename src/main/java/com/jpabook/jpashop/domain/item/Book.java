package com.jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") //싱글 테이블 전략에서 각 아이템을 구분하기 위한 값
@Getter @Setter
public class Book extends Item {

	private String author;
	private String isbn;
}
