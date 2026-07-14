package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SampleApprovalImagesVO;

@Repository
public interface SampleApprovalImagesRepo extends JpaRepository<SampleApprovalImagesVO, Long> {

}
