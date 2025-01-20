package com.PhysicalTrack.user;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PhysicalTrack.account.dto.AccountDto;
import com.PhysicalTrack.user.dto.User;
import com.PhysicalTrack.user.dto.UserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	/**
	 * 유저 등록 API
	 * @param userDto
	 */
	public void addUser(UserDto userDto) {
		
		// 1. SignUpRequestDto를 User Entity로 변환
		User user = User.builder()
				.deviceId(userDto.getDeviceId())
				.name(userDto.getName())
				.gender(userDto.getGender())
				.birthYear(userDto.getBirthYear())
				.build();
		
		// 2. User Entity 저장
		userRepository.save(user);
	}
	
	/**
	 * 유저 정보 조회 (단건)
	 * @param deviceId
	 * @return userDto ? null
	 */
	public UserDto getUserByDeviceId(String deviceId) {
		User user = userRepository.findByDeviceId(deviceId);
		UserDto userDto = null;
		
		if (user != null) {
			userDto = new UserDto(user.getUserId(), user.getDeviceId(), user.getName(), user.getGender(), user.getBirthYear());
		}
		
		return userDto;
	}
	

	/**
	 * UserIds로 UserNames 조회 (리스트)
	 * @param userIds
	 * @return Map<UserId, Name>
	 */
	public Map<Integer, String> getUserNamesByIds(List<Integer> userIds) {
		return userRepository.findAllById(userIds).stream()
			            .collect(Collectors.toMap(
			                User::getUserId,    
			                User::getName
			            ));
    }
	
	/**
	 * Get Name By UserId (단건)
	 * @param userId
	 * @return name
	 */
	public String getUserNameByUserId(int userId) {
		return userRepository.findById(userId).orElse(null).getName();
	}
	
	/**
	 * 유저 정보 수정 API
	 * @param accountDto -- AccountController
	 */
	public void updateUser(AccountDto accountDto) {
		// 기존 User
		User exisingUser = userRepository.findById(accountDto.getUserId()).orElse(null);
		
		// 업데이트 User
		User updatedUser = exisingUser.toBuilder()
									.name(accountDto.getName())
									.gender(accountDto.getGender())
									.birthYear(accountDto.getBirthYear())
									.build();
		
		// 업데이트 반영
		userRepository.save(updatedUser);
	}
	
	/**
	 * 회원탈퇴 - 유저 삭제 API
	 * @param userId
	 */
	public void deleteUser(int userId) {
		userRepository.deleteById(userId);
	}
	
	// test code
	public List<User> test3() {
		return userRepository.findAll();
	}
}
