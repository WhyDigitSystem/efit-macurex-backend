package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SampleApprovalDetailsVO;
import com.efitops.basesetup.entity.SampleApprovalVO;

@Repository
public interface SampleApprovalDetailsRepo extends JpaRepository<SampleApprovalDetailsVO, Long>{

	List<SampleApprovalDetailsVO> findBySampleApprovalVO(SampleApprovalVO sampleApprovalVO);

}
