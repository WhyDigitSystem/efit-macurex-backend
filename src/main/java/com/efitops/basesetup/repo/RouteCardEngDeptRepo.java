package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RouteCardEngDeptVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;

@Repository
public interface RouteCardEngDeptRepo extends JpaRepository<RouteCardEngDeptVO, Long>{

	List<RouteCardEngDeptVO> findByRouteCardEntryVO(RouteCardEntryVO routeCardEntryVO);

}
