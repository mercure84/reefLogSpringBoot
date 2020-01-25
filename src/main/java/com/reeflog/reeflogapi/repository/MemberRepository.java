package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {


    Member findByEmail(String email);


}
