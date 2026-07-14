package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.UserActionVO;



@Repository
public interface UserActionRepo extends JpaRepository<UserActionVO, Long>{

}
