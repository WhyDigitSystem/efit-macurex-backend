package com.efitops.basesetup.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.TokenVO;

@Repository
public interface TokenRepo extends JpaRepository<TokenVO, String>{

}

