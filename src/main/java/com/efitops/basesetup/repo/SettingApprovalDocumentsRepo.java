package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SettingApprovalDocumentsVO;
import com.efitops.basesetup.entity.SettingApprovalVO;

@Repository
public interface SettingApprovalDocumentsRepo extends JpaRepository<SettingApprovalDocumentsVO, Long>{


	List<SettingApprovalDocumentsVO> findBySettingApprovalVO(SettingApprovalVO settingApprovalVO);

}
