package com.PhysicalTrack.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.PhysicalTrack.user.dto.UserDto;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	// 유저 등록 API
	public void addUser(UserDto userDto) {
		
		// 1. SignUpRequestDto를 User Entity로 변환
		User user = User.builder()
				.deviceId(userDto.getDeviceId())
				.name(userDto.getName())
				.gender(userDto.getGender())
				.age(userDto.getAge())
				.build();
		
		// 2. User Entity 저장
		userRepository.save(user);
	}
	
	/**
	 * 유저 정보 조회
	 * @param deviceId
	 * @return userDto ? null
	 */
	public UserDto getUserByDeviceId(String deviceId) {
		User user = userRepository.findByDeviceId(deviceId);
		UserDto userDto = null;
		
		if (user != null) {
			userDto = new UserDto(user.getUserId(), user.getDeviceId(), user.getName(), user.getGender(), user.getAge());
		}
		
		return userDto;
	}
	

	// test code
	public List<User> test3() {
		return userRepository.findAll();
	}
}
