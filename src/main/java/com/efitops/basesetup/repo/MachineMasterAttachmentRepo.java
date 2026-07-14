package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.MachineMasterAttachmentVO;
import com.efitops.basesetup.entity.MachineMasterVO;

@Repository
public interface MachineMasterAttachmentRepo extends JpaRepository<MachineMasterAttachmentVO, Long> {

	List<MachineMasterAttachmentVO> findByMachineMasterVO(MachineMasterVO inprocessInspectionVO);

}
