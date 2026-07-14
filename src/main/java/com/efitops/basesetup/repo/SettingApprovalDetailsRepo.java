package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SettingApprovalDetailsVO;
import com.efitops.basesetup.entity.SettingApprovalVO;

@Repository
public interface SettingApprovalDetailsRepo extends JpaRepository<SettingApprovalDetailsVO, Long>{

	List<SettingApprovalDetailsVO> findBySettingApprovalVO(SettingApprovalVO settingApprovalVO);

}
