package com.efitops.basesetup.security;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.efitops.basesetup.entity.UserVO;
import com.efitops.basesetup.repo.UserRepo;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	UserRepo userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserVO user = userRepository.findByUserName(email);
		if (ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("User not found with email : " + email);
		}
		return UserPrincipal.create(user);
	}

	public UserDetails loadUserById(Long id) {
		UserVO user = userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with ID : " + id));
		return UserPrincipal.create(user);
	}

}