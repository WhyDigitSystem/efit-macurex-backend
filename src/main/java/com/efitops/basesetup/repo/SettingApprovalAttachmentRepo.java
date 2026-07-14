package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SettingApprovalAttachmentVO;
import com.efitops.basesetup.entity.SettingApprovalVO;

@Repository
public interface SettingApprovalAttachmentRepo extends JpaRepository<SettingApprovalAttachmentVO, Long> {

	List<SettingApprovalAttachmentVO> findBySettingApprovalVO(SettingApprovalVO settingApprovalVO);

}
