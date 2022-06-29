package com.kabigon.board.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kabigon.board.model.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
