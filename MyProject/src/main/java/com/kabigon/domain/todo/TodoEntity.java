package com.kabigon.domain.todo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kabigon.domain.common.BaseEntity;
import com.kabigon.domain.converter.BooleanToYNConverter;
import com.kabigon.domain.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TODO_TBL")
public class TodoEntity extends BaseEntity {
	@Id
	@Column(name = "TODO_NO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long todoNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_NO", nullable = false)
	private UserEntity todoUser;
	
	@Column(name = "TODO_CONTENT", nullable = false)
	private String todoContent;
	
	@Column(name = "TODO_COLOR")
	private String todoColor;
	
	@Column(name = "IS_COMPLETE")
	@Convert(converter = BooleanToYNConverter.class)
	private Boolean isComplete;
	
	@Column(name = "EXPIRY_DATE", nullable = false)
	private LocalDateTime expiryDate;
	
	public void updateTodo() {
		isComplete = !isComplete;
	}
}
