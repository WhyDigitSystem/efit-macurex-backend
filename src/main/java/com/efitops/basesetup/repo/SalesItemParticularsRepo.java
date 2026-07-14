package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesItemParticularsVO;
import com.efitops.basesetup.entity.SalesVO;

@Repository
public interface SalesItemParticularsRepo extends JpaRepository<SalesItemParticularsVO, Long> {

	List<SalesItemParticularsVO> findBySalesVO(SalesVO salesVO);

}
