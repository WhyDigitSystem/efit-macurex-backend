package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DocumentNumberChangeDetailsVO;
import com.efitops.basesetup.entity.DocumentNumberChangeVO;

@Repository
public interface DocumentNumberChangeDetailsRepo extends JpaRepository<DocumentNumberChangeDetailsVO, Long> {

	List<DocumentNumberChangeDetailsVO> findByDocumentNumberChangeVO(DocumentNumberChangeVO documentNumberChangeVO);

	// NEW SAVE
	List<DocumentNumberChangeDetailsVO> findAllByDocumentFormateNoAndIssueNo(String documentFormateNo, Long issueNo);

	List<DocumentNumberChangeDetailsVO> findAllByDocumentFormateNoAndRevisionNo(String documentFormateNo,
			Long revisionNo);

	// UPDATE MODE
	List<DocumentNumberChangeDetailsVO> findAllByDocumentFormateNoAndIssueNoAndDocumentNumberChangeVO_IdNot(
			String documentFormateNo, Long issueNo, Long id);

	List<DocumentNumberChangeDetailsVO> findAllByDocumentFormateNoAndRevisionNoAndDocumentNumberChangeVO_IdNot(
			String documentFormateNo, Long revisionNo, Long id);
}
