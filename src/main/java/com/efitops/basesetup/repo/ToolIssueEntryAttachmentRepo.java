package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ToolIssueEntryAttachmentVO;
import com.efitops.basesetup.entity.ToolIssueEntryVO;

public interface ToolIssueEntryAttachmentRepo extends JpaRepository<ToolIssueEntryAttachmentVO,Long> {

	List<ToolIssueEntryAttachmentVO> findByToolIssueEntryVO(ToolIssueEntryVO toolIssueEntryVO);

}
