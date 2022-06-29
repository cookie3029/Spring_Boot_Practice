package com.kabigon.board.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude="member")
public class Board extends BaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bno;
	private String title;
	private String content;

	// Member Entity를 N:1 관계로 참조
	@ManyToOne(fetch=FetchType.LAZY)
	private Member member;
	
	// title을 수정하는 메서드
	public void changeTitle(String title) {
		this.title = title;
	}
	
	// content를 수정하는 메서드
	public void changeContent(String content) {
		this.content = content;
	}
}
