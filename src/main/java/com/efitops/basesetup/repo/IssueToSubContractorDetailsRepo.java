package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.IssueToSubContractorDetailsVO;
import com.efitops.basesetup.entity.IssueToSubContractorVO;

@Repository
public interface IssueToSubContractorDetailsRepo extends JpaRepository<IssueToSubContractorDetailsVO, Long> {

	List<IssueToSubContractorDetailsVO> findByIssueToSubContractorVO(IssueToSubContractorVO issueToSubContractorVO);

}
