package com.PhysicalTrack.consistency;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PhysicalTrack.consistency.dto.Consistency;

public interface ConsistencyRepository extends JpaRepository<Consistency, Integer> {
	
	List<Consistency> findAllByOrderByStreakCountDesc();
	
	// 회원탈퇴 - 꾸준함 삭제
	void deleteByUserId(int userId);
}
