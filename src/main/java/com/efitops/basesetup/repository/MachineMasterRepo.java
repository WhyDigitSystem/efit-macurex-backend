package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MachineMasterVO;

public interface MachineMasterRepo extends JpaRepository<MachineMasterVO, Long> {

}
