package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.IssuesDetailsVO;
import com.efitops.basesetup.entity.IssuesVO;

public interface IssuesDetailsRepo extends JpaRepository<IssuesDetailsVO, Long> {
	
	
	void deleteByIssuesVO(IssuesVO issuesVO);

}
