package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.TSBankVO;

public interface TSBankRepo extends JpaRepository<TSBankVO, Long>{

}
