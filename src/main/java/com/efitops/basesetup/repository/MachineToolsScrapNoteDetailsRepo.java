package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MachineToolsScrapNoteDetailsVO;
import com.efitops.basesetup.entity.MachineToolsScrapNoteVO;

public interface MachineToolsScrapNoteDetailsRepo extends JpaRepository<MachineToolsScrapNoteDetailsVO, Long> {

	List<MachineToolsScrapNoteDetailsVO> findByMachineToolsScrapNoteVO(MachineToolsScrapNoteVO vo);

}
