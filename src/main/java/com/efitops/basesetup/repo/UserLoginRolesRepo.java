package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.UserLoginRolesVO;
import com.efitops.basesetup.entity.UserVO;


public interface UserLoginRolesRepo extends JpaRepository<UserLoginRolesVO, Long> {

	List<UserLoginRolesVO> findByUserVO(UserVO userVO);

}