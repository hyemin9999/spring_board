package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;

	// 인증+인가를 같이 한다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// username으로 사용자 정보 검색 ==> 인증
		Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);

		if (_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
		}

		SiteUser siteUser = _siteUser.get();
		// Granted ==> 권한
		List<GrantedAuthority> authorities = new ArrayList<>();

		// 관리자(UserRole.ADMIN)나 사용자(UserRole.USER)권한을 준다. ==> 인가
		if ("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}

		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}
}
