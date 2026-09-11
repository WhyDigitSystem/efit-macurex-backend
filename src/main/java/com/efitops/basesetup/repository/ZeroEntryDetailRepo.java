package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ZeroEntryDetailVO;
import com.efitops.basesetup.entity.ZeroKmFailureEntryVO;

public interface ZeroEntryDetailRepo extends JpaRepository<ZeroEntryDetailVO, Long> {
	
	
	 List<ZeroEntryDetailVO> findByZeroKmFailureEntryVO(
	            ZeroKmFailureEntryVO zeroKmFailureEntryVO);

}
