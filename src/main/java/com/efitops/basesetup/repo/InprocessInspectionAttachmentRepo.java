package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InprocessInspectionAttachmentVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;

@Repository
public interface InprocessInspectionAttachmentRepo extends JpaRepository<InprocessInspectionAttachmentVO, Long> {

	List<InprocessInspectionAttachmentVO> findByInprocessInspectionVO(InprocessInspectionVO inprocessInspectionVO);

}
