package com.kabigon.memo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="memo")

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Memo extends BaseEntity {
	// 시스템이 생성해주는 랜덤한 문자열로 하고 싶을 경우
	// @Id
	// @GeneratedValue(generator="system-uuid")
	// @GenericGenerator(name="system-uuid", strategy="uuid")
	// private String id;
	
	// gno 값을 데이터베이스의 auto_increment나 sequence를 이용해서 생성
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	// 메모 번호
	private Long gno;
	
	// 메모 제목
	@Column(length=100, nullable=false)
	private String title;
	
	// 메모 내용
	@Column(length=1500, nullable=false)
	private String content;
	
	// 메모 작성자
	@Column(length=100, nullable=false)
	private String writer;
	
	// title을 변경해주는 메서드
	public void changeTitle(String title) {
		this.title = title;
	}
	
	// content를 변경해주는 메서드
	public void changeContent(String content) {
		this.content = content;
	}
}
