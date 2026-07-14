package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RouteCardEntryAttachmentVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;

@Repository
public interface RouteCardEntryAttachmentRepo extends JpaRepository<RouteCardEntryAttachmentVO, Long> {

	List<RouteCardEntryAttachmentVO> findByRouteCardEntryVO(RouteCardEntryVO routeCardEntryVO);

}
