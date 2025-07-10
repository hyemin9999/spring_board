package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 사용자 저장
	 * 
	 * @param username 이름 == ID
	 * @param email    이메일
	 * @param password 비밀번호 == 암호화
	 * @return 사용자
	 */
	public SiteUser create(String username, String email, String password) {

		SiteUser user = new SiteUser();

		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));

		this.userRepository.save(user);

		return user;
	}

	/**
	 * 사용자 반환
	 * 
	 * @param username 사용자 검색을 위한 이름 == ID
	 * @return 사용자
	 */
	public SiteUser getUser(String username) {

		Optional<SiteUser> siteUser = this.userRepository.findByusername(username);

		if (siteUser.isPresent()) {
			return siteUser.get();
		} else {
			throw new DataNotFoundException("siteuser not found");
		}
	}

	/**
	 * 사용자 수정
	 * 
	 * @param item     수정할 사용자
	 * @param email    이메일
	 * @param password 비밀번호
	 */
	public void modify(SiteUser item, String email, String password) {

		if (email != "") {
			item.setEmail(email);
		}

		if (password != "") {
			item.setPassword(passwordEncoder.encode(password));
		}

		this.userRepository.save(item);
	}
}
