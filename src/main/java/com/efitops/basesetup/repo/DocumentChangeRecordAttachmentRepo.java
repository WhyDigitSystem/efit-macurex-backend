package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DocumentChangeRecordAttachmentVO;
import com.efitops.basesetup.entity.QualityDocumentChangeRecordVO;

@Repository
public interface DocumentChangeRecordAttachmentRepo extends JpaRepository<DocumentChangeRecordAttachmentVO, Long> {

	List<DocumentChangeRecordAttachmentVO> findByQualityDocumentChangeRecordVO(
			QualityDocumentChangeRecordVO qualityDocumentChangeRecordVO);

}
