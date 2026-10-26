package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.MachineToolScrapNoteAttachmentVO;
import com.efitops.basesetup.entity.MachineToolsScrapNoteVO;

public interface MachineToolsScrapNoteAttachmentRepo extends JpaRepository<MachineToolScrapNoteAttachmentVO, Long>{

	List<MachineToolScrapNoteAttachmentVO> findByMachineToolsScrapNoteVO(MachineToolsScrapNoteVO vo);

}
