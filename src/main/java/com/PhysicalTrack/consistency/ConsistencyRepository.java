package com.PhysicalTrack.consistency;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PhysicalTrack.consistency.dto.Consistency;

public interface ConsistencyRepository extends JpaRepository<Consistency, Integer> {

}
