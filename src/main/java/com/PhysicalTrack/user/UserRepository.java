package com.PhysicalTrack.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByDeviceId(String deviceId);
}
