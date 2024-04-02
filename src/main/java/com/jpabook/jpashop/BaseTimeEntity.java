package com.jpabook.jpashop;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseTimeEntity {

	@CreatedDate
	@Column(name = "createdAt", updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updateAt")
	private LocalDateTime updateAt;
}
