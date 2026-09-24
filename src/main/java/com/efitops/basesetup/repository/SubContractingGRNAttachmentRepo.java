package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractingGRNAttachmentVO;
import com.efitops.basesetup.entity.SubContractingGRNVO;

@Repository
public interface SubContractingGRNAttachmentRepo extends JpaRepository<SubContractingGRNAttachmentVO, Long> {

	List<SubContractingGRNAttachmentVO> findBySubContractingGRNVO(SubContractingGRNVO subContractingGRNVO);

}
