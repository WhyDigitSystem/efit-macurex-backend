package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MachineMasterVO;
import com.efitops.basesetup.entity.MachineSpareDetailsVO;

public interface MachineSpareDetailsRepo
        extends JpaRepository<MachineSpareDetailsVO, Long> {

	List<MachineSpareDetailsVO> findByMachineMasterVO(MachineMasterVO vo);

}