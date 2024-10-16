package com.PhysicalTrack.user;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// test code
	public List<User> test3() {
		return userRepository.findAll();
	}
}
