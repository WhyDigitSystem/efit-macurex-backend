package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GstVO;

@Repository
public interface GstRepo extends JpaRepository<GstVO, Long> {

	@Query(nativeQuery = true, value = "select * from gst  where orgid=?1")
	List<GstVO> getAllGstByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from gst where gstid=?1")
	List<GstVO> getGstById(Long id);

	boolean existsByGstSlabAndOrgId(String gstSlab, Long orgId);

}
