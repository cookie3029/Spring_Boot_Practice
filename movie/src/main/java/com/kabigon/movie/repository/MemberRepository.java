package com.kabigon.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabigon.movie.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
