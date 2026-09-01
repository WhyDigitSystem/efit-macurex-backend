package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ToolMasterVO;

public interface ToolMasterRepo extends JpaRepository<ToolMasterVO, Long>{

}
