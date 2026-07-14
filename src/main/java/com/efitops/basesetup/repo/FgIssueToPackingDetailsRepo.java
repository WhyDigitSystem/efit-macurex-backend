package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.FgIssueToPackingDetailsVO;
import com.efitops.basesetup.entity.FgIssueToPackingVO;

@Repository
public interface FgIssueToPackingDetailsRepo extends JpaRepository<FgIssueToPackingDetailsVO, Long> {

	List<FgIssueToPackingDetailsVO> findByFgIssueToPackingVO(FgIssueToPackingVO fgIssueToPackingVO);


}
