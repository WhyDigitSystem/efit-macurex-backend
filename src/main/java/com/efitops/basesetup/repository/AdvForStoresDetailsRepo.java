package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.AdvForStoresDetailsVO;
import com.efitops.basesetup.entity.AdvForStoresVO;

@Repository
public interface AdvForStoresDetailsRepo  extends JpaRepository<AdvForStoresDetailsVO, Long>{

	List<AdvForStoresDetailsVO> findByAdvForStoresVO(AdvForStoresVO advForStoresVO);

}
