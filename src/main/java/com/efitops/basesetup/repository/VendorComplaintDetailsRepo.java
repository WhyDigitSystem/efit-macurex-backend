package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.VendorComplaintDetailsVO;
import com.efitops.basesetup.entity.VendorComplaintEntryVO;

public interface VendorComplaintDetailsRepo extends JpaRepository<VendorComplaintDetailsVO, Long>{

	List<VendorComplaintDetailsVO> findByVendorComplaintEntryVO(VendorComplaintEntryVO vendorComplaintEntryVO);

}
