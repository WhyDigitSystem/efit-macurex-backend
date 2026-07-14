package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.NCProductRegisterDetailsAttachmentVO;
import com.efitops.basesetup.entity.NcProductRegisterVO;

@Repository
public interface NCProductRegisterDetailsAttachmentRepo extends JpaRepository<NCProductRegisterDetailsAttachmentVO, Long> {

	List<NCProductRegisterDetailsAttachmentVO> findByNcProductRegisterVO(NcProductRegisterVO ncProductRegisterVO);

}
