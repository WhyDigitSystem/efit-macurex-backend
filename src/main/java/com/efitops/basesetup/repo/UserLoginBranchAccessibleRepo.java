package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.UserLoginBranchAccessibleVO;


public interface UserLoginBranchAccessibleRepo extends JpaRepository<UserLoginBranchAccessibleVO, Long> {

}
