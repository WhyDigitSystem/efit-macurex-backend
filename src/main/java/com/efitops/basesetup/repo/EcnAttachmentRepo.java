package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.EcnApprovalRecordVO;
import com.efitops.basesetup.entity.EcnAttachmentVO;

public interface EcnAttachmentRepo extends JpaRepository<EcnAttachmentVO, Long>{

	List<EcnAttachmentVO> findByEcnApprovalRecordVO(EcnApprovalRecordVO ecnApprovalRecordVO);

}
