package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RouteCardEntryDetailsVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;

@Repository
public interface RouteCardEntryDetailsRepo extends JpaRepository<RouteCardEntryDetailsVO, Long> {

	List<RouteCardEntryDetailsVO> findByRouteCardEntryVO(RouteCardEntryVO routeCardEntryVO);

}
