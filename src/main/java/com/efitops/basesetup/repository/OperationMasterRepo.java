package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.OperationMasterVO;

public interface OperationMasterRepo extends JpaRepository<OperationMasterVO, Long> {

}
