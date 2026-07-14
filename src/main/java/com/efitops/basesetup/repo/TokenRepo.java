package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.TokenVO;

public interface TokenRepo extends JpaRepository<TokenVO, String>{

}
