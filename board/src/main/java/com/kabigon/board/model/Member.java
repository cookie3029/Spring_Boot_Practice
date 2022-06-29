package com.kabigon.board.model;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@Entity 
@Table(name="tbl_member")

@Builder 
@Getter 
@ToString
@AllArgsConstructor
@NoArgsConstructor 
public class Member extends BaseEntity {
	@Id
	private String email;
	private String password;
	private String name;
}
