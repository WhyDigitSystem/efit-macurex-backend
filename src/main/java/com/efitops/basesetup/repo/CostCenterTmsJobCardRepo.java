package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.CostCenterTmsJobCardVO;
import com.efitops.basesetup.entity.TmsJobCardVO;
@Repository
public interface CostCenterTmsJobCardRepo extends JpaRepository<CostCenterTmsJobCardVO, Long> {

	List<CostCenterTmsJobCardVO> findByTmsJobCardVO(TmsJobCardVO tmsJobCardVO);

}
