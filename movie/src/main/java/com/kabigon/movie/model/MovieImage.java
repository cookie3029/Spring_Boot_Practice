package com.kabigon.movie.model;

import javax.persistence.Embeddable;
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
// 다른 Entity에 포함될 수 있다라는 의미의 어노테이션
// 여러 개로 구성되어 있지만 하나의 값처럼 사용한다는 의미
@Embeddable	
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude="movie")
public class MovieImage extends BaseEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long inum;
	
	private String uuid;
	
	private String imgName;
	
	private String path;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Movie movie;
}
