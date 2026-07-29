package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.HolidayMasterDetailsVO;
import com.efitops.basesetup.entity.HolidayMasterVO;

public interface HolidayMasterDetailsRepo extends JpaRepository<HolidayMasterDetailsVO, Long>{

	List<HolidayMasterDetailsVO> findByHolidayMasterVO(HolidayMasterVO holidayMasterVO);


}
