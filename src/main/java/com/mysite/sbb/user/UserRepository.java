package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//Repository ==> interface, extends JpaRepository
public interface UserRepository extends JpaRepository<SiteUser, Long> {
	Optional<SiteUser> findByusername(String username);
}
