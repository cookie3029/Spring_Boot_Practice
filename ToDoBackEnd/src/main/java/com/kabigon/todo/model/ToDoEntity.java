package com.kabigon.todo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)

@Builder // 생성자를 이용하지 않고 인스턴스를 생성하고 속성 이름을 setter처럼 사용하기 위한 어노테이션
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="todo")
public class ToDoEntity extends BaseEntity {
	@Id
	// 시스템이 랜덤하게 uuid로 기본키 값을 생성
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	private String id;
	
	@Column(length=100, nullable=false)
	private String userId;
	
	@Column(length=100, nullable=false)
	private String title;
	
	@Column(nullable=false)
	private boolean done;
}
