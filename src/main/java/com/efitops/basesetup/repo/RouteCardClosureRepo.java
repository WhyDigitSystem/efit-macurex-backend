package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RouteCardClosureVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;

@Repository
public interface RouteCardClosureRepo extends JpaRepository<RouteCardClosureVO, Long>{

	List<RouteCardClosureVO> findByRouteCardEntryVO(RouteCardEntryVO routeCardEntryVO);


}
